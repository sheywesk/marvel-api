package com.sheywesk.marvel_api.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sheywesk.marvel_api.data.models.Character

class FakeCharacterRepository : ICharacterRepository {
    private var shouldReturnError = false

    fun setShouldReturnError(value:Boolean){
        shouldReturnError = value
    }

    override suspend fun findCharacterById(id: Int): Character? {
        TODO("Not yet implemented")
    }

    override fun getAllCharacter(): LiveData<Result<List<Character>>> {
        return if(shouldReturnError){
            MutableLiveData(Result.success(listOf<Character>()))
        }else{
           MutableLiveData(Result.failure(Exception("Ocorreu um erro - test")))
        }
    }
}