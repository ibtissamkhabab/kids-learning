package com.example.enfantapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlphabetAdapter(
    private val alphabets: List<AlphabetItem>,
    private val onAlphabetClick: (AlphabetType) -> Unit
) : RecyclerView.Adapter<AlphabetAdapter.AlphabetViewHolder>() {

    data class AlphabetItem(
        val type: AlphabetType,
        val title: String,
        val imageRes: Int
    )

    class AlphabetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.alphabetTitle)
        val imageView: ImageView = view.findViewById(R.id.alphabetImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alphabet, parent, false)
        return AlphabetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        val alphabet = alphabets[position]
        holder.titleText.text = alphabet.title
        holder.imageView.setImageResource(alphabet.imageRes)

        holder.itemView.setOnClickListener {
            onAlphabetClick(alphabet.type)
        }
    }

    override fun getItemCount() = alphabets.size
}
