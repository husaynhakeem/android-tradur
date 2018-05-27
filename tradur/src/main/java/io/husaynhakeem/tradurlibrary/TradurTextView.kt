package io.husaynhakeem.tradurlibrary

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class TradurTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var viewState: TradurViewState = TradurViewState()
    private val textTranslator: TextTranslator by lazy { TextTranslator() }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TradurTextView, defStyleAttr, 0)
        viewState = viewState.copy(translatableViewResId = typedArray.getResourceId(R.styleable.TradurTextView_translate_field, 0))
        typedArray.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (viewState.translatableViewResId == 0) {
            throw RuntimeException("Resource identifier for attribute translate_field not defined")
        }
        setUpTranslationWidget()
    }

    private fun setUpTranslationWidget() {
        val translatableTextView = getTranslatableTextViewFrom(viewState.translatableViewResId)
        this.setOnClickListener {
            if (viewState.hasTranslatableTextBeenTranslated) {
                revertToOriginalText(translatableTextView)
            } else {
                viewState = viewState.copy(originalTranslatableText = translatableTextView.text.toString())
                translateText(translatableTextView)
            }
            viewState = viewState.copy(hasTranslatableTextBeenTranslated = !viewState.hasTranslatableTextBeenTranslated)
        }
    }

    private fun getTranslatableTextViewFrom(resId: Int): TextView {
        val contextCopy = context
        return when (contextCopy) {
            is AppCompatActivity -> contextCopy.findViewById<TextView>(resId)
            is Fragment -> contextCopy.activity?.findViewById(resId)
            else -> throw RuntimeException("Context not activity or fragment")
        } ?: throw RuntimeException("View for $resId is not a textView")
    }

    private fun revertToOriginalText(translatableTextView: TextView) {
        updateTexts(R.string.default_see_translation, translatableTextView, viewState.originalTranslatableText)
    }

    private fun translateText(translatableTextView: TextView) {
        textTranslator.translate(
                translatableTextView.text.toString(),
                onStart = { this@TradurTextView.setText(R.string.default_loading) },
                onSuccess = { updateTexts(R.string.default_see_original, translatableTextView, it) },
                onFailure = {
                    updateTexts(R.string.default_see_translation, translatableTextView, viewState.originalTranslatableText)
                    viewState = viewState.copy(hasTranslatableTextBeenTranslated = false)
                })
    }

    private fun updateTexts(translatorTextResId: Int, translatableTextView: TextView, translatableTextViewText: String) {
        this@TradurTextView.setText(translatorTextResId)
        translatableTextView.text = translatableTextViewText
    }
}