package com.example.ttsreader.data

import java.util.*

data class TtsVoice(
    val voiceName: String,
    val locale: Locale,
    val normalizedLocale: Locale,
    val isNetworkConnectionRequired: Boolean,
)