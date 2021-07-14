package com.sheywesk.marvel_api.data.api

import dev.thiagosouto.butler.file.readFile
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import com.google.common.truth.Truth.assertThat
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.models.Image

class MarvelApiTest {

    @Test
    fun `should hit endpoints with expected parameters`() = runBlocking {
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(readFile("character_marvel.json"))
        )

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

        server.shutdown()
        assertThat(result.body()?.data?.results).isEqualTo(expect)
    }
}