package com.sheywesk.marvel_api.data.datasource.remote

import com.google.common.truth.Truth.assertThat
import com.sheywesk.marvel_api.data.api.MarvelApi
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.models.Image
import dev.thiagosouto.butler.file.readFile
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class RemoteDataSourceTest {

    lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `request character from marvel api,return success`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(readFile("character_marvel.json"))
        server.enqueue(mockResponse)
        val retrofit = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: MarvelApi = retrofit.create(MarvelApi::class.java)
        val result = service.getCharacter()
        val expect = listOf(
            Character(
                id = 1011334,
                name = "3-D Man",
                description = "",
                thumbnail = Image(
                    path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                    extension = "jpg"
                )
            )
        )
        assertThat(result.code()).isEqualTo(HttpURLConnection.HTTP_OK)
        assertThat(result.body()?.data?.results).isEqualTo(expect)
    }
}