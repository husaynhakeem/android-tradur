package io.husaynhakeem.tradur

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation.getClient
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


internal class TextTranslator {

    suspend fun translate(text: String): String {
        try {
            // Get language we're translating from
            val fromTag = identifyLanguage(text)
            val from = getLanguage(fromTag)

            // Get language we're translating to
            val toTag = Locale.getDefault().language
            val to = getLanguage(toTag)

            // Build translator
            val options = TranslatorOptions.Builder()
                    .setSourceLanguage(from)
                    .setTargetLanguage(to)
                    .build()
            val translator = getClient(options)

            // Download language model
            downloadModel(translator)

            // Return translated text
            return translate(translator, text)
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to translate [$text]", exception)
            return String.empty
        }
    }

    private suspend fun identifyLanguage(text: String): String {
        return suspendCoroutine { continuation ->
            val languageIdentifier = LanguageIdentification.getClient()
            languageIdentifier.identifyLanguage(text)
                    .addOnSuccessListener { languageCode ->
                        if (languageCode == UNDEFINED_LANGUAGE) {
                            Log.i(TAG, "Identified language is undefined")
                            continuation.resumeWithException(LanguageIdentificationException(
                                    "Identified language is undefined"))
                        } else {
                            Log.i(TAG, "Successfully identified language [$languageCode] from " +
                                    "[$text]")
                            continuation.resume(languageCode)
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Model could not be loaded or other internal error.
                        Log.e(TAG, "Failed to identify language", exception)
                        continuation.resumeWithException(LanguageIdentificationException(exception))
                    }
        }
    }

    private fun getLanguage(tag: String): String {
        val language = TranslateLanguage.fromLanguageTag(tag)
        Log.i(TAG, "Language [$language] corresponds to tag [$tag]")

        if (language == null) {
            throw IllegalArgumentException("Unknown language tag $tag")
        }
        return language
    }

    private suspend fun downloadModel(translator: Translator) {
        return suspendCoroutine { continuation ->
            val conditions = DownloadConditions.Builder()
                    .requireWifi()
                    .build()
            translator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener {
                        // Model downloaded successfully. Okay to start translating.
                        Log.i(TAG, "Successfully downloaded model")
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { exception ->
                        // Model couldn’t be downloaded or other internal error.
                        Log.e(TAG, "Failed to download model", exception)
                        continuation.resumeWithException(ModelDownloadException(exception))
                    }
        }
    }

    private suspend fun translate(translator: Translator, text: String): String {
        return suspendCoroutine { continuation ->
            translator.translate(text)
                    .addOnSuccessListener { translation ->
                        Log.i(TAG, "Successfully translated [$text] into [$translation]")
                        continuation.resume(translation)
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Failed to translate [$text]", exception)
                        continuation.resumeWithException(TranslationException(exception))
                    }
        }
    }

    companion object {
        private const val TAG = "TextTranslator"
        private const val UNDEFINED_LANGUAGE = "und"
    }
}
