package com.example.enfantapp

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private var soundPool: SoundPool? = null
    private val clickSoundId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
            
        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val card1 = view.findViewById<LinearLayout>(R.id.card1)
        card1.setOnClickListener {
            playClickSound()
            navigateToAlphabet(AlphabetType.FRENCH)
        }

        val card2 = view.findViewById<LinearLayout>(R.id.card2)
        card2.setOnClickListener {
            playClickSound()
            navigateToAlphabet(AlphabetType.ARABIC)
        }
    }

    private fun playClickSound() {

        android.media.ToneGenerator(android.media.AudioManager.STREAM_MUSIC, 50).apply {
            startTone(android.media.ToneGenerator.TONE_DTMF_0, 150)
            release()
        }
    }

    private fun navigateToAlphabet(type: AlphabetType) {
        val fragment = AlphabetListFragment.newInstance(type)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool?.release()
        soundPool = null
    }
}