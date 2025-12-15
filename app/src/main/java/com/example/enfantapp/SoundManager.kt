package com.example.enfantapp

import android.content.Context
import android.media.MediaPlayer

/**
 * Gestionnaire de sons pour l'application
 * Joue la prononciation des lettres
 */
class SoundManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    /**
     * Joue le son correspondant à une lettre
     */
    fun playLetterSound(letter: Letter) {
        // Arrêter le son précédent s'il joue encore
        stopSound()

        // Obtenir l'ID de la ressource audio
        val soundResId = getSoundResourceId(letter)

        if (soundResId != 0) {
            try {
                mediaPlayer = MediaPlayer.create(context, soundResId)
                mediaPlayer?.setOnCompletionListener {
                    stopSound()
                }
                mediaPlayer?.start()
            } catch (e: Exception) {
                // Si le fichier audio n'existe pas, jouer un son de secours
                playFallbackSound(letter)
            }
        } else {
            // Pas de fichier audio, utiliser le son de secours
            playFallbackSound(letter)
        }
    }

    /**
     * Obtient l'ID de la ressource audio pour une lettre
     */
    private fun getSoundResourceId(letter: Letter): Int {
        val soundName = if (letter.type == AlphabetType.FRENCH) {
            // Pour le français: a, b, c, etc.
            "letter_${letter.character.lowercase()}"
        } else {
            // Pour l'arabe: mapper les lettres aux noms de fichiers
            getArabicSoundName(letter.character)
        }

        return context.resources.getIdentifier(soundName, "raw", context.packageName)
    }

    /**
     * Obtient le nom du fichier son pour une lettre arabe
     */
    private fun getArabicSoundName(character: String): String {
        return when (character) {
            "ا" -> "letter_alif"
            "ب" -> "letter_ba"
            "ت" -> "letter_ta"
            "ث" -> "letter_tha"
            "ج" -> "letter_jim"
            "ح" -> "letter_ha"
            "خ" -> "letter_kha"
            "د" -> "letter_dal"
            "ذ" -> "letter_dhal"
            "ر" -> "letter_ra"
            "ز" -> "letter_zay"
            "س" -> "letter_sin"
            "ش" -> "letter_shin"
            "ص" -> "letter_sad"
            "ض" -> "letter_dad"
            "ط" -> "letter_tah"
            "ظ" -> "letter_zah"
            "ع" -> "letter_ayn"
            "غ" -> "letter_ghayn"
            "ف" -> "letter_fa"
            "ق" -> "letter_qaf"
            "ك" -> "letter_kaf"
            "ل" -> "letter_lam"
            "م" -> "letter_mim"
            "ن" -> "letter_nun"
            "ه" -> "letter_haa"
            "و" -> "letter_waw"
            "ي" -> "letter_ya"
            else -> "letter_alif" // Par défaut
        }
    }

    /**
     * Son de secours si le fichier audio n'existe pas
     */
    private fun playFallbackSound(letter: Letter) {
        android.media.ToneGenerator(
            android.media.AudioManager.STREAM_MUSIC,
            80
        ).apply {
            startTone(letter.soundTone, 300)
            release()
        }
    }

    /**
     * Arrête le son en cours
     */
    fun stopSound() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = null
    }

    /**
     * Libère les ressources
     */
    fun release() {
        stopSound()
    }
}
