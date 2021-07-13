package com.sheywesk.marvel_api.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelService {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: MarvelApi = retrofit.create(MarvelApi::class.java)
}