package com.sheywesk.marvel_api.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.sheywesk.marvel_api.data.datasource.local.ILocalDataSource
import com.sheywesk.marvel_api.data.datasource.remote.IRemoteDataSource
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.utils.Resource

class FakeDataSource(private var characterList: MutableList<Character> = mutableListOf()) :
    IRemoteDataSource, ILocalDataSource {
    private val observableCharacter = MutableLiveData(characterList)
    private var shouldReturnNetworkStatus = true
    private var shouldReturnLocalStatus = true
    fun setShouldReturnNetworkStatus(value: Boolean) {
        shouldReturnNetworkStatus = value
    }

    fun setShouldReturnLocalStatus(value: Boolean) {
        shouldReturnLocalStatus = value
    }

    override suspend fun remoteGetAllCharacter(): Resource<List<Character>> {
        return if (shouldReturnNetworkStatus) {
            Resource.success(characterList.toList())
        } else {
            Resource.error(data = null, msg = "Erro de conexão - test")
        }
    }

    override suspend fun updateFavorite(character: Character) {
        TODO("Not yet implemented")
    }

    override fun localGetAllCharacter(): LiveData<Resource<List<Character>>> {
        return if (shouldReturnLocalStatus) {
            observableCharacter.map { Resource.success(it) }
        } else {
            observableCharacter.map {
                Resource.error(
                    data = null,
                    msg = "banco de dados vazio - test"
                )
            }
        }
    }

    override suspend fun localGetAllCharacterSync(): Resource<List<Character>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveCharacter(character: Character) {
        characterList.add(character)
    }

    override suspend fun findCharacterById(id: Int): Resource<Character> {
        val character = characterList.find { return@find it.id == id }
        return if (character != null) {
            Resource.success(character)
        } else {
            Resource.error(data = null, msg = "Personagem não existe")
        }

    }
}