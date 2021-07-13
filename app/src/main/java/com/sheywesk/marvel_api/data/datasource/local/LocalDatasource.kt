package com.sheywesk.marvel_api.data.datasource.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.sheywesk.marvel_api.R
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.utils.Resource

class LocalDatasource(private val characterDao: CharacterDao, private val context: Context) :
    ILocalDataSource {
    override suspend fun saveCharacter(character: Character) {
        characterDao.save(character)
    }

    override suspend fun findCharacterById(id: Int): Character? {
        return characterDao.findCharacterById(id)
    }

    override suspend fun updateFavorite(character: Character) {
        characterDao.updateFavorite(character)
    }

    override fun localGetAllCharacter(): LiveData<Resource<List<Character>>> {
        return if (characterDao.checkIsNull().value != 0) {
            characterDao.getAllCharacter().map {
                Resource.success(it)
            }
        } else {
            MutableLiveData(
                Resource.error(
                    data = null,
                    msg = context.getString(R.string.database_empty)
                )
            )
        }
    }

    override suspend fun localGetAllCharacterSync(): Resource<List<Character>> {
        return if (characterDao.checkIsNull().value != 0) {
            Resource.success(data = characterDao.getAllCharacterSync())
        } else {
            Resource.error(data = null, msg = context.getString(R.string.database_empty))
        }
    }
}