package io.husaynhakeem.tradurlibrary


data class TradurViewState(
        val originalTranslatableText: String = "",
        val translatableViewResId: Int = 0,
        val hasTranslatableTextBeenTranslated: Boolean = false)