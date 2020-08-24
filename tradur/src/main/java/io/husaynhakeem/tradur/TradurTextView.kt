package io.husaynhakeem.tradur

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import io.husaynhakeem.tradurlibrary.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TradurTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), View.OnClickListener {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val translator = TextTranslator()

    // Translatable TextView
    private var translatableTextViewId: Int = -1
    private val translatableTextView: TextView by lazy {
        ViewTreeSearcher().findTextViewWithIdClosestTo(this, translatableTextViewId)
                ?: throw RuntimeException("TradurTextView could not find a TextView of which to " +
                        "translate the contents.")
    }

    // Possible values for the translatable TextView's text
    private val original: String by lazy { translatableTextView.text.toString() }
    private var translation: String = String.empty

    // Possible values for TradurTextView's text
    private val loading: String
    private val preTranslation: String
    private val postTranslation: String

    // Initially the original text is displayed
    private var mode = Mode.ORIGINAL

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TradurTextView, defStyleAttr, 0)

        translatableTextViewId = typedArray.getResourceId(R.styleable.TradurTextView_translate_field, -1)
        if (translatableTextViewId == -1) {
            throw IllegalStateException("Resource identifier for attribute app:translate_field " +
                    "must be defined")
        }

        loading = typedArray.getStringOrDefault(R.styleable.TradurTextView_loading_text, R.string.default_loading_text)
        preTranslation = typedArray.getStringOrDefault(R.styleable.TradurTextView_pre_translation_text, R.string.default_pre_translation_text)
        postTranslation = typedArray.getStringOrDefault(R.styleable.TradurTextView_post_translation_text, R.string.default_post_translation_text)

        typedArray.recycle()
        setOnClickListener(this)

        this@TradurTextView.text = preTranslation
    }

    private fun TypedArray.getStringOrDefault(index: Int, defaultValResId: Int): String {

        // Try to return the string from a string resource id
        val stringResId = getResourceId(index, -1)
        if (stringResId != -1) {
            return context.getString(stringResId)
        }

        // Try to return the string from a string literal
        val stringLiteral = getString(index)
        if (stringLiteral != null) {
            return stringLiteral
        }

        // Return the default string
        return context.getString(defaultValResId)
    }

    override fun onClick(view: View?) {
        when {
            mode == Mode.TRANSLATED -> {
                mode = Mode.ORIGINAL
                translatableTextView.text = original
                this@TradurTextView.text = preTranslation
            }
            translation.isNotBlank() -> {
                mode = Mode.TRANSLATED
                translatableTextView.text = translation
                this@TradurTextView.text = postTranslation
            }
            else -> translateText()
        }
    }

    private fun translateText() {
        uiScope.launch {

            // Loading
            this@TradurTextView.text = loading

            // Get translation
            translation = withContext(Dispatchers.IO) {
                translator.translate(original)
            }

            if (translation.isBlank()) {

                // Translation failed
                this@TradurTextView.text = preTranslation

            } else {

                // Translation successful
                mode = Mode.TRANSLATED
                translatableTextView.text = translation
                this@TradurTextView.text = postTranslation
            }
        }
    }

    private enum class Mode {
        ORIGINAL, TRANSLATED
    }
}