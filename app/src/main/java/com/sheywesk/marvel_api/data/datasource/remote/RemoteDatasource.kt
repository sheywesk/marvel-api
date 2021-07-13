package com.sheywesk.marvel_api.data.datasource.remote

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sheywesk.marvel_api.R
import com.sheywesk.marvel_api.data.api.MarvelService
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.models.CharacterResult
import com.sheywesk.marvel_api.utils.NetworkManager
import com.sheywesk.marvel_api.utils.Resource
import retrofit2.Response

class RemoteDatasource(
    private val marvelService: MarvelService,
    private val context: Context
) : IRemoteDataSource {
    override suspend fun remoteGetAllCharacter(): Resource<List<Character>> {
        return if (NetworkManager.isOnline(context)) {
            return try {
                val response = marvelService.service.getCharacter()
                return if (response.isSuccessful) {
                    val responseBody = response.body()
                    return if (responseBody != null) {
                        Resource.success(responseBody.data.results)
                    } else {
                        Resource.error(
                            data = null,
                            msg = context.getString(R.string.unexpected_error)
                        )
                    }
                } else {
                    Resource.error(data = null, msg = context.getString(R.string.unexpected_error))
                }
            } catch (e: Exception) {
                Resource.error(data = null, msg = context.getString(R.string.unexpected_error))
            }
        } else {
            Resource.error(data = null, msg = context.getString(R.string.no_conection))
        }
    }
}