package com.sheywesk.marvel_api.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sheywesk.marvel_api.R
import com.sheywesk.marvel_api.data.datasource.local.ILocalDataSource
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.datasource.remote.IRemoteDataSource
import com.sheywesk.marvel_api.utils.Resource
import com.sheywesk.marvel_api.utils.Status
import java.lang.Exception

class CharacterRepository(
    private val remoteDatasource: IRemoteDataSource,
    private val context: Context,
    private val localDatasource: ILocalDataSource,
) : ICharacterRepository {

    override suspend fun findCharacterById(id: Int): Resource<Character> {
        return localDatasource.findCharacterById(id)
    }

    override fun getAllCharacter(): LiveData<Resource<List<Character>>> = liveData {
        try {
            val response = remoteDatasource.remoteGetAllCharacter()
            if (response.status == Status.SUCCESS) {
                response.data?.forEach { character ->
                    /* This check is necessary to not save favorite item
                      because remote object is favorite = false */
                    val localData = localDatasource.findCharacterById(character.id)
                    if (localData.status== Status.ERROR) {
                        localDatasource.saveCharacter(character)
                    }
                }
            }
            emitSource(localDatasource.localGetAllCharacter())
        } catch (e: Exception) {
            emit(Resource.error(data = null, msg = context.getString(R.string.unexpected_error)))
        }
    }

    override suspend fun localGetAllCharacterSync(): Resource<List<Character>> {
        return localDatasource.localGetAllCharacterSync()
    }

    override suspend fun updateFavorite(character: Character) {
        localDatasource.updateFavorite(character)
    }


}