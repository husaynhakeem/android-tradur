package io.husaynhakeem.tradur

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.husaynhakeem.tradurlibrary.R


class TradurTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var viewState: TradurViewState = TradurViewState()
    private val textTranslator: TextTranslator by lazy { TextTranslator() }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TradurTextView, defStyleAttr, 0)
        val translatableViewResId = typedArray.getResourceId(R.styleable.TradurTextView_translate_field, DEFAULT_RES_ID)
        val loadingText = getStringValue(typedArray, R.styleable.TradurTextView_loading_text, R.string.default_loading_text)
        val preTranslationText = getStringValue(typedArray, R.styleable.TradurTextView_pre_translation_text, R.string.default_pre_translation_text)
        val postTranslationText = getStringValue(typedArray, R.styleable.TradurTextView_post_translation_text, R.string.default_post_translation_text)
        viewState = viewState.copy(
                translatableViewResId = translatableViewResId,
                loadingText = loadingText,
                preTranslationText = preTranslationText,
                postTranslationText = postTranslationText)
        typedArray.recycle()
    }

    private fun getStringValue(typedArray: TypedArray, index: Int, defaultValResId: Int): String {
        val resId = typedArray.getResourceId(index, DEFAULT_RES_ID)
        if (resId != DEFAULT_RES_ID) {
            return context.getString(resId)
        }
        return typedArray.getString(index) ?: context.getString(defaultValResId)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (viewState.translatableViewResId == DEFAULT_RES_ID) {
            throw RuntimeException("Resource identifier for attribute app:translate_field not defined")
        }
        setUpTranslationWidget()
    }

    private fun setUpTranslationWidget() {
        this.text = viewState.preTranslationText
        val translatableTextView = getTranslatableTextView(viewState.translatableViewResId)
        this.setOnClickListener {
            if (viewState.hasTranslatableTextBeenTranslated) {
                revertToOriginalText(translatableTextView)
                viewState = viewState.copy(hasTranslatableTextBeenTranslated = false)
            } else {
                viewState = viewState.copy(originalTranslatableText = translatableTextView.text.toString())
                translateText(translatableTextView)
            }
        }
    }

    private fun getTranslatableTextView(resId: Int): TextView {
        val contextCopy = context
        return when (contextCopy) {
            is AppCompatActivity -> contextCopy.findViewById<TextView>(resId)
            is Fragment -> contextCopy.activity?.findViewById(resId)
            else -> throw RuntimeException("TradurTextView context neither an activity or fragment")
        } ?: throw RuntimeException("View for $resId is not a textView")
    }

    private fun revertToOriginalText(translatableTextView: TextView) {
        updateTexts(viewState.preTranslationText, translatableTextView, viewState.originalTranslatableText)
    }

    private fun translateText(translatableTextView: TextView) {
        textTranslator.translate(
                translatableTextView.text.toString(),
                onStart = { this@TradurTextView.text = viewState.loadingText },
                onSuccess = {
                    updateTexts(viewState.postTranslationText, translatableTextView, it)
                    viewState = viewState.copy(hasTranslatableTextBeenTranslated = true)
                },
                onFailure = {
                    updateTexts(viewState.preTranslationText, translatableTextView, viewState.originalTranslatableText)
                    viewState = viewState.copy(hasTranslatableTextBeenTranslated = false)
                })
    }

    private fun updateTexts(translatorText: String, translatableTextView: TextView, translatableTextViewText: String) {
        this@TradurTextView.text = translatorText
        translatableTextView.text = translatableTextViewText
    }

    companion object {
        private const val DEFAULT_RES_ID = 0
    }
}