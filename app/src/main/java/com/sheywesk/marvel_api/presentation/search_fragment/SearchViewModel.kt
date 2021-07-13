package com.sheywesk.marvel_api.presentation.search_fragment

import androidx.lifecycle.*
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.repository.ICharacterRepository
import com.sheywesk.marvel_api.utils.Resource
import com.sheywesk.marvel_api.utils.Status
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: ICharacterRepository) : ViewModel() {
    private val _filteredCharacter = MutableLiveData<Resource<List<Character>>>()
    val filteredCharacter: LiveData<Resource<List<Character>>>
        get() = _filteredCharacter

    init {
        viewModelScope.launch {
            _filteredCharacter.postValue( repository.localGetAllCharacterSync())
        }
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
        repository.getAllCharacter()
    }

    fun filterByName(name: String) {
        val newList = mutableListOf<Character>()
        val list = mutableListOf<Character>()

        viewModelScope.launch {
            val response = repository.localGetAllCharacterSync()
            if(name.isEmpty() || name.isBlank()){
                if(response.status == Status.SUCCESS){
                    response.data?.let{
                        newList.addAll(response.data)
                    }
                }
            }else{
                if(response.status == Status.SUCCESS){
                    response.data?.let{
                        list.addAll(response.data)
                    }
                }
                list.let {
                    it.forEach {character ->
                        if (character.name.uppercase().contains(name.uppercase() as CharSequence)) {
                            newList.add(character)
                        }
                    }
                }
            }
            _filteredCharacter.postValue(Resource.success(newList.toList()))
        }
    }
}