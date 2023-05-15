package com.example.ttsreader.data

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val TAG = "TTS"
class TtsRepository @Inject constructor(@ApplicationContext private val application: Context) {

    private var ttsEngine: TextToSpeech? = null
    private val iso3Languages by lazy { Locale.getISOLanguages().associateBy { Locale(it).isO3Language } }
    private val iso3Countries by lazy { Locale.getISOCountries().associateBy { Locale("en", it).isO3Country } }

    private suspend fun init() {
        if (ttsEngine == null) {
            suspendCancellableCoroutine {
                ttsEngine = TextToSpeech(application) { status ->
                    Log.e(TAG, "init callback status $status")
                    if (status == TextToSpeech.SUCCESS) {
                        it.resume(Unit)
                    } else {
                        it.resumeWithException(IllegalStateException("TTS init failed with $status"))
                    }
                }
            }
        }
    }

    private fun deinit() {
        Log.e(TAG, "shutdown TTS")
        ttsEngine?.shutdown()
        ttsEngine = null
    }

    suspend fun getVoiceList(): List<TtsVoice> {
        init()
        val voices = ttsEngine?.voices?.map { voice ->
            TtsVoice(
                voiceName = voice.name,
                locale = voice.locale,
                normalizedLocale = normalizeLocale(voice.locale),
                isNetworkConnectionRequired = voice.isNetworkConnectionRequired,
            )
        } ?: emptyList()
        Log.e(TAG, "loaded voices $voices")
        deinit()
        return voices
    }

    private fun normalizeLocale(locale: Locale): Locale {
        var language = locale.language
        var country = locale.country
        if (iso3Languages.containsKey(language)) language = iso3Languages[language]
        if (iso3Countries.containsKey(country)) country = iso3Countries[country]
        return Locale(language, country)
    }

}