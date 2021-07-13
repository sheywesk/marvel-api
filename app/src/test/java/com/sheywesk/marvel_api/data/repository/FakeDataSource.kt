package com.sheywesk.marvel_api.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.sheywesk.marvel_api.data.datasource.local.ILocalDataSource
import com.sheywesk.marvel_api.data.datasource.remote.IRemoteDataSource
import com.sheywesk.marvel_api.data.models.Character

class FakeDataSource(private var characterList: MutableList<Character> = mutableListOf()) :
    IRemoteDataSource, ILocalDataSource {
    private val observableCharacter = MutableLiveData(characterList)
    private var shouldReturnNetworkStatus = true

    fun setShouldReturnNetworkStatus(value: Boolean) {
        shouldReturnNetworkStatus = value
    }

    override suspend fun remoteGetAllCharacter(): Result<List<Character>> {
        return if (shouldReturnNetworkStatus) {
            Result.success(characterList.toList())
        } else {
            Result.failure(Exception("Erro de conexão"))
        }
    }

    override fun localGetAllCharacter(): LiveData<Result<List<Character>>> {
        return observableCharacter.map { Result.success(it) }
    }

    override suspend fun saveCharacter(character: Character) {
        characterList.add(character)
    }

    override suspend fun findCharacterById(id: Int): Character? {
        TODO("Not yet implemented")
    }
}