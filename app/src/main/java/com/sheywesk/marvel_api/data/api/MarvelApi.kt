package com.sheywesk.marvel_api.data.api

import com.sheywesk.marvel_api.data.models.CharacterResult
import com.sheywesk.marvel_api.extensions.md5
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.sql.Timestamp
import java.util.*

interface MarvelApi {
    @GET("public/characters?")
    suspend fun getCharacter(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("ts") ts: String = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString(),
        @Query("hash") hash: String = "$ts$PRIVATE_KEY$API_KEY".md5()
    ): Response<CharacterResult>
}