package com.sheywesk.marvel_api.data.datasource.remote

import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.utils.Resource

interface IRemoteDataSource {
    suspend fun remoteGetAllCharacter():Resource<List<Character>>
}