package com.sheywesk.marvel_api.data.datasource.local

import androidx.lifecycle.LiveData
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.utils.Resource

interface ILocalDataSource {
    suspend fun updateFavorite(character: Character)
    fun localGetAllCharacter(): LiveData<Resource<List<Character>>>
    suspend fun localGetAllCharacterSync(): Resource<List<Character>>
    suspend fun saveCharacter(character: Character)
    suspend fun findCharacterById(id: Int): Character?
}