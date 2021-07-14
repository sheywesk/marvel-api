package com.sheywesk.marvel_api.data.repository

import androidx.lifecycle.LiveData
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.utils.Resource

interface ICharacterRepository {
   suspend fun findCharacterById(id: Int):Resource<Character>
   fun getAllCharacter(): LiveData<Resource<List<Character>>>
   suspend fun localGetAllCharacterSync(): Resource<List<Character>>
   suspend fun updateFavorite(character: Character)
}