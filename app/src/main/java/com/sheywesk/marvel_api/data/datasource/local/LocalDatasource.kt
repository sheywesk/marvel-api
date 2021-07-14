package com.sheywesk.marvel_api.data.datasource.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.sheywesk.marvel_api.R
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.utils.Resource

class LocalDatasource(
    private val characterDao: CharacterDao,
    private val context: Context
) :
    ILocalDataSource {
    override suspend fun saveCharacter(character: Character) {
        characterDao.save(character)
    }

    override suspend fun findCharacterById(id: Int): Resource<Character> {
        val response = characterDao.findCharacterById(id)
        return if (response == null) {
            Resource.error(data = null, msg = context.getString(R.string.character_dont_exist))
        } else {
            Resource.success(data = response)
        }
    }

    override suspend fun updateFavorite(character: Character) {
        characterDao.updateFavorite(character)
    }

    override fun localGetAllCharacter(): LiveData<Resource<List<Character>>> = liveData {
        if (characterDao.checkIsNull() != 0) {
            val characterList = characterDao.getAllCharacter().map {
                Resource.success(it)
            }
            emitSource(characterList)
        } else {
            val characterError = Resource.error(
                data = null,
                msg = context.getString(R.string.database_empty)
            )
            emit(characterError)
        }
    }

    override suspend fun localGetAllCharacterSync(): Resource<List<Character>> {
        return if (characterDao.checkIsNull() != 0) {
            Resource.success(data = characterDao.getAllCharacterSync())
        } else {
            Resource.error(data = null, msg = context.getString(R.string.database_empty))
        }
    }
}