package com.sheywesk.marvel_api.presentation.character_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sheywesk.marvel_api.R
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.databinding.CharacterItemBinding
import com.sheywesk.marvel_api.databinding.NameAndFavoriteBinding

class CharacterViewHolder(private val item: CharacterItemBinding) :
    RecyclerView.ViewHolder(item.root) {
    fun bind(
        character: Character,
        onItemClick: (item: Character) -> Unit,
        onFavoriteClick: (item: Character) -> Unit
    ) {
        with(item) {
            infoCard.characterName.text = character.name
            val url =
                "${character.thumbnail.path}/portrait_xlarge.${character.thumbnail.extension}".replace(
                    "http",
                    "https"
                )
            Glide.with(item.root)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_image)
                .into(thumbnail)
            if (character.favorite) {
                infoCard.favorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        item.root.context,
                        R.drawable.ic_star
                    )
                )
            } else {
                infoCard.favorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        item.root.context,
                        R.drawable.ic_star_outline
                    )
                )
            }
            infoCard.favorite.setOnClickListener {
                onFavoriteClick(character)
            }
        }
        item.root.setOnClickListener {
            onItemClick(character)
        }
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return CharacterViewHolder(view)
        }
    }
}