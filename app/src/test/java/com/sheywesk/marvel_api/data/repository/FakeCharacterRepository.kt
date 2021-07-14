package com.sheywesk.marvel_api.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.models.Image
import com.sheywesk.marvel_api.utils.Resource

class FakeCharacterRepository : ICharacterRepository {
    private var shouldReturnError = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun findCharacterById(id: Int): Resource<Character> {
        return if (shouldReturnError) {
            Resource.error(data = null, msg = "Personagem não encontrado")
        } else {
            Resource.success(
                data = Character(
                    0, "", "",
                    false, Image("", "")
                )
            )
        }
    }

    override fun getAllCharacter(): LiveData<Resource<List<Character>>> {
        return if (shouldReturnError) {
            MutableLiveData(Resource.success(listOf<Character>()))
        } else {
            MutableLiveData(Resource.error(data = null, msg = "Ocorreu um erro - test"))
        }
    }

    override suspend fun localGetAllCharacterSync(): Resource<List<Character>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateFavorite(character: Character) {
        TODO("Not yet implemented")
    }
}