package com.sheywesk.marvel_api.presentation.character_list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sheywesk.marvel_api.data.models.Character

class CharacterAdapter(
    private val onItemClick: (character: Character) -> Unit,
    private val onFavoriteClick: (character: Character) -> Unit
) : ListAdapter<Character, CharacterViewHolder>(CharacterComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick, onFavoriteClick)
    }

    class CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.favorite == newItem.favorite
        }
    }
}