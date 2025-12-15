package com.example.enfantapp


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlphabetListFragment : Fragment() {

    private lateinit var alphabetType: AlphabetType
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LetterAdapter
    private var soundManager: SoundManager? = null

    companion object {
        private const val ARG_TYPE = "alphabet_type"

        fun newInstance(type: AlphabetType): AlphabetListFragment {
            val fragment = AlphabetListFragment()
            val args = Bundle()
            args.putSerializable(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alphabetType = arguments?.getSerializable(ARG_TYPE) as? AlphabetType ?: AlphabetType.FRENCH
        
        // Initialiser le SoundManager
        soundManager = SoundManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alphabet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleText = view.findViewById<TextView>(R.id.titleText)
        titleText.text = if (alphabetType == AlphabetType.FRENCH) "Alphabet Français" else "الأبجدية العربية"

        val btnBack = view.findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        recyclerView = view.findViewById(R.id.recyclerViewLetters)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)// grille 2 item

        // Configuration RTL pour l'arabe
        if (alphabetType == AlphabetType.ARABIC) {
            recyclerView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        val letters = getLetters()

        adapter = LetterAdapter(letters) { letter ->
            onLetterClicked(letter)
        }

        recyclerView.adapter = adapter
    }

    private fun getLetters(): List<Letter> {
        return if (alphabetType == AlphabetType.FRENCH) {
            getFrenchLetters()
        } else {
            getArabicLetters()
        }
    }

    private fun getFrenchLetters(): List<Letter> {
        val letters = mutableListOf<Letter>()
        for (c in 'A'..'Z') {
            letters.add(
                Letter(
                    character = c.toString(),
                    type = AlphabetType.FRENCH,
                    soundTone = android.media.ToneGenerator.TONE_DTMF_0 + (c - 'A') % 12
                )
            )
        }
        return letters
    }

    private fun getArabicLetters(): List<Letter> {
        val arabicLetters = listOf(
            "ا", "ب", "ت", "ث", "ج", "ح", "خ", "د", "ذ", "ر", "ز",
            "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ك",
            "ل", "م", "ن", "ه", "و", "ي"
        )

        return arabicLetters.mapIndexed { index, char ->
            Letter(
                character = char,
                type = AlphabetType.ARABIC,
                soundTone = android.media.ToneGenerator.TONE_DTMF_0 + (index % 12)
            )
        }
    }

    private fun onLetterClicked(letter: Letter) {


        val fragment = DrawingFragment.newInstance(letter)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun playLetterSound(letter: Letter) {
        // Jouer la prononciation réelle de la lettre
        soundManager?.playLetterSound(letter)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager?.release()
        soundManager = null
    }
}
