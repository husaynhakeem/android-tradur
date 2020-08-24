package io.husaynhakeem.tradur

internal class LanguageIdentificationException : Exception {

    constructor(message: String) : super(message)

    constructor(cause: Exception) : super(cause)
}

internal class ModelDownloadException(cause: Exception) : Exception(cause)

internal class TranslationException(cause: Exception) : Exception(cause)