package io.husaynhakeem.tradur

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.TextView
import io.husaynhakeem.tradurlibrary.R


class TradurTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var viewState: TradurViewState = TradurViewState()
    private val translatableTextView: TextView by lazy {
        ViewTreeSearcher.findTextViewWithIdClosestTo(this, viewState.translatableViewResId)
                ?: throw RuntimeException("View for ${viewState.translatableViewResId} is not a textView")
    }

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
        updateUI()
        setOnClickListener {
            if (viewState.isTextTranslated) {
                viewState = viewState.copy(
                        isTextTranslated = false,
                        translationState = TranslationState.PRE_TRANSLATION)
                updateUI()
            } else {
                viewState = viewState.copy(originalText = translatableTextView.text.toString())
                translateText()
            }
        }
    }

    private fun translateText() {
        TextTranslator.translate(
                viewState.originalText,
                onStart = {
                    viewState = viewState.copy(translationState = TranslationState.LOADING)
                    updateUI()
                },
                onSuccess = {
                    viewState = viewState.copy(
                            translationState = TranslationState.POST_TRANSLATION,
                            translationText = it,
                            isTextTranslated = true)
                    updateUI()
                },
                onFailure = {
                    viewState = viewState.copy(
                            translationState = TranslationState.PRE_TRANSLATION,
                            isTextTranslated = false)
                    updateUI()
                })
    }

    private fun updateUI() {
        with(viewState) {
            if (isTextTranslated) {
                if (translationText.isNotBlank())
                    translatableTextView.text = translationText
            } else {
                if (originalText.isNotBlank())
                    translatableTextView.text = originalText
            }
            when (translationState) {
                TranslationState.LOADING -> this@TradurTextView.text = loadingText
                TranslationState.PRE_TRANSLATION -> this@TradurTextView.text = preTranslationText
                TranslationState.POST_TRANSLATION -> this@TradurTextView.text = postTranslationText
            }
        }
    }

    companion object {
        private const val DEFAULT_RES_ID = 0
    }
}