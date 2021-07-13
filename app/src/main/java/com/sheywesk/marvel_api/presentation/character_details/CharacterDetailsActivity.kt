package com.sheywesk.marvel_api.presentation.character_details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.sheywesk.marvel_api.R
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.databinding.ActivityCharacterDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailsActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_ID = "EXTRA_ID"
        fun getIntent(context: Context, id: Int): Intent {
            return Intent(context, CharacterDetailsActivity::class.java).apply {
                putExtra(EXTRA_ID, id)
            }
        }
    }

    private val characterDetailsViewModel: CharacterDetailsViewModel by viewModel()

    private val binding: ActivityCharacterDetailsBinding by lazy {
        ActivityCharacterDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpObservables()
    }

    private fun setUpObservables() {
        characterDetailsViewModel.finCharacterById(intent.extras?.getInt(EXTRA_ID)!!)
        characterDetailsViewModel.character.observe(this, {
            it?.let {
                showDetails(it)
            }
        })
    }

    private fun showDetails(character: Character) {
        val url =
            "${character.thumbnail.path}/portrait_xlarge.${character.thumbnail.extension}".replace(
                "http",
                "https"
            )
        with(binding) {
            nameDetails.characterName.text = character.name
            Glide.with(binding.root)
                .load(url).centerCrop().into(thumbnail)
            description.text = character.description

            if (character.favorite) {
                nameDetails.favorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_star
                    )
                )
            } else {
                nameDetails.favorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.ic_star_outline
                    )
                )
            }
        }
    }
}