package com.example.enfantapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class DrawingFragment : Fragment() {

    private lateinit var letter: Letter
    private lateinit var drawingView: DrawingView
    private lateinit var letterDisplay: TextView
    private lateinit var btnSound: ImageView
    private lateinit var btnClear: ImageView
    private var soundManager: SoundManager? = null

    companion object {
        private const val ARG_LETTER = "letter_data"

        fun newInstance(letter: Letter): DrawingFragment {
            val fragment = DrawingFragment()
            val args = Bundle()
            args.putString(ARG_LETTER + "_char", letter.character)
            args.putSerializable(ARG_LETTER + "_type", letter.type)
            args.putInt(ARG_LETTER + "_tone", letter.soundTone)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val char = arguments?.getString(ARG_LETTER + "_char") ?: "A"
        val type = arguments?.getSerializable(ARG_LETTER + "_type") as? AlphabetType ?: AlphabetType.FRENCH
        val tone = arguments?.getInt(ARG_LETTER + "_tone") ?: android.media.ToneGenerator.TONE_DTMF_0

        letter = Letter(char, type, tone)
        
        // Initialiser le SoundManager pour les vrais sons
        soundManager = SoundManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drawing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        letterDisplay = view.findViewById(R.id.letterDisplay)
        drawingView = view.findViewById(R.id.drawingView)
        btnSound = view.findViewById(R.id.btnSound)
        btnClear = view.findViewById(R.id.btnClear)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        val btnPrev = view.findViewById<ImageView>(R.id.btnPrev)
        val btnNext = view.findViewById<ImageView>(R.id.btnNext)
        val strokeSeekBar = view.findViewById<SeekBar>(R.id.strokeSizeSeekBar)

        val lettersOrder: List<String> = if (letter.type == AlphabetType.FRENCH) {
            ('A'..'Z').map { it.toString() }
        } else {
            listOf(
                "ا", "ب", "ت", "ث", "ج", "ح", "خ", "د", "ذ", "ر", "ز",
                "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ك",
                "ل", "م", "ن", "ه", "و", "ي"
            )
        }

        var currentIndex = lettersOrder.indexOf(letter.character).coerceAtLeast(0)

        fun letterForIndex(index: Int): Letter {
            val char = lettersOrder[index]
            val tone = android.media.ToneGenerator.TONE_DTMF_0 + (index % 12)
            return Letter(character = char, type = letter.type, soundTone = tone)
        }

        fun applyLetterStyleAndNavState() {
            letterDisplay.text = letter.character

            if (letter.type == AlphabetType.ARABIC) {
                letterDisplay.textSize = 180f
                letterDisplay.layoutDirection = View.LAYOUT_DIRECTION_RTL
            } else {
                letterDisplay.textSize = 200f
                letterDisplay.layoutDirection = View.LAYOUT_DIRECTION_LTR
            }

            // Style contour
            letterDisplay.paint.style = android.graphics.Paint.Style.STROKE
            letterDisplay.paint.strokeWidth = 8f
            letterDisplay.setTextColor(0xFF2196F3.toInt())
            letterDisplay.alpha = 0.5f

            // Etat des boutons
            btnPrev.isEnabled = currentIndex > 0
            btnPrev.alpha = if (btnPrev.isEnabled) 1f else 0.3f
            btnNext.isEnabled = currentIndex < lettersOrder.size - 1
            btnNext.alpha = if (btnNext.isEnabled) 1f else 0.3f
        }

        fun goToIndex(newIndex: Int) {
            if (newIndex < 0 || newIndex >= lettersOrder.size) return

            soundManager?.stopSound()
            currentIndex = newIndex
            letter = letterForIndex(currentIndex)

            drawingView.clear()
            applyLetterStyleAndNavState()
        }

        applyLetterStyleAndNavState()
//-----------------------------------------------------------------
        btnPrev.setOnClickListener {
            goToIndex(currentIndex - 1)  // A → retour
        }

        btnNext.setOnClickListener {
            goToIndex(currentIndex + 1) // A → B → C
        }

        // Slider épaisseur du trait: 8..40px
        fun applyStrokeFromSeek(seekValue: Int) {
            val min = 8f
            val max = 40f
            val width = min + (max - min) * (seekValue / 100f)
            drawingView.setStrokeWidth(width)
        }
//---------------------------------------------------------------

        // Valeur initiale
        applyStrokeFromSeek(strokeSeekBar.progress)

        strokeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                applyStrokeFromSeek(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
//btn effacer et son les deux ---------------------------------------
        btnSound.setOnClickListener {
            playDefaultSound()
        }

        btnClear.setOnClickListener {
            drawingView.clear()
        }

        // Ne pas jouer automatiquement - l'enfant clique sur le bouton quand il veut
        // playDefaultSound()
    }

    private fun playDefaultSound() {
        // Jouer la prononciation réelle de la lettre
        soundManager?.playLetterSound(letter)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager?.release()
        soundManager = null
    }
}