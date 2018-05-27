package io.husaynhakeem.tradur


internal data class TradurViewState(
        // Translatable view and text
        val translatableViewResId: Int = 0,
        val originalText: String = EMPTY,
        val translationText: String = EMPTY,

        // Translator view state and text
        val translationState: TranslationState = TranslationState.PRE_TRANSLATION,
        val loadingText: String = EMPTY,
        val preTranslationText: String = EMPTY,
        val postTranslationText: String = EMPTY,

        // Is original text translated or not
        val isTextTranslated: Boolean = false)