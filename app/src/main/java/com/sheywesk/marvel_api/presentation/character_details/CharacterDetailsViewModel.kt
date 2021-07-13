package com.sheywesk.marvel_api.presentation.character_details

import androidx.lifecycle.*
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.repository.ICharacterRepository
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(private val repository: ICharacterRepository) : ViewModel() {
    private val _character = MutableLiveData<Character>()
    val character: LiveData<Character>
        get() = _character

    fun finCharacterById( id:Int){
        viewModelScope.launch {
         val character = repository.findCharacterById(id)
            character?.let {
                _character.postValue(it)
            }
        }
    }
}