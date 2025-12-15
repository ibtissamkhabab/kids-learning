package com.example.enfantapp

import android.media.ToneGenerator

enum class AlphabetType {
    FRENCH,
    ARABIC
}

data class Letter(
    val character: String,
    val type: AlphabetType,
    val soundTone: Int = ToneGenerator.TONE_DTMF_0
)