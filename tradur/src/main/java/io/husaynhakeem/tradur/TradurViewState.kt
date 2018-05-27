package io.husaynhakeem.tradur


internal data class TradurViewState(
        val originalTranslatableText: String = EMPTY,
        val translatableViewResId: Int = 0,
        val loadingText: String = EMPTY,
        val preTranslationText: String = EMPTY,
        val postTranslationText: String = EMPTY,
        val hasTranslatableTextBeenTranslated: Boolean = false)