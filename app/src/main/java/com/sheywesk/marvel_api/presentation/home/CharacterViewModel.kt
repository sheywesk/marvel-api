package com.sheywesk.marvel_api.presentation.home

import androidx.lifecycle.*
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.repository.ICharacterRepository
import com.sheywesk.marvel_api.utils.Resource
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: ICharacterRepository) : ViewModel() {
    val marvelViewModel: LiveData<Resource<List<Character>>>
        get() = liveData {
            emit(Resource.loading(data = null))
            emitSource(repository.getAllCharacter())
        }

    fun updateFavorite(character: Character) {
        // New object is necessary to calculate listAdapter diff
        val newCharacter = Character(
            character.id,
            character.name,
            character.description,
            !character.favorite,
            character.thumbnail
        )
        viewModelScope.launch {
            repository.updateFavorite(newCharacter)
        }
    }
}


