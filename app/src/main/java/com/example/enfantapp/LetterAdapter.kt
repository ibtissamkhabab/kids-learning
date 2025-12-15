package com.example.enfantapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LetterAdapter(
    private val letters: List<Letter>,
    private val onLetterClick: (Letter) -> Unit
) : RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {

    class LetterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val letterText: TextView = view.findViewById(R.id.letterText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_letter, parent, false)
        return LetterViewHolder(view)
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val letter = letters[position]
        holder.letterText.text = letter.character

        if (letter.type == AlphabetType.ARABIC) {
            holder.letterText.textSize = 32f
        } else {
            holder.letterText.textSize = 36f
        }

        holder.itemView.setOnClickListener {
            onLetterClick(letter)
        }
    }

    override fun getItemCount() = letters.size
}
