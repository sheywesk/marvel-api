package com.sheywesk.marvel_api.data.datasource.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.models.Image
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class LocalDataSourceTest {
    private lateinit var localDataSource: ILocalDataSource
    private lateinit var database: CharacterDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, CharacterDatabase::class.java
        ).build()
        localDataSource = LocalDatasource(database.characterDao(), context)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveCharacter_retrievesCharacter() = runBlocking {
        val expect = Character(
            0,
            "teste1", "teste1",
            false, Image("", "")
        )
        localDataSource.saveCharacter(expect)

        val actual = localDataSource.findCharacterById(0)

        assertThat(expect).isEqualTo(actual.data)
    }

    @Test
    fun updateFavorite() = runBlocking {
        val character = Character(
            0,
            "teste1", "teste1",
            false, Image("", "")
        )
        localDataSource.saveCharacter(character)
        val characterToUpdate = localDataSource.findCharacterById(0)
        characterToUpdate.data?.favorite = true
        localDataSource.updateFavorite(characterToUpdate.data!!)

        val expect = Character(
            0,
            "teste1", "teste1",
            true, Image("", "")
        )
        val actual = localDataSource.findCharacterById(0)
        assertThat(expect).isEqualTo(actual.data)
    }

    @Test
    fun localGetAllCharacterSync() = runBlocking {
        val character = listOf(
            Character(
                0,
                "teste1", "teste1",
                false, Image("", "")
            )
        )
        localDataSource.saveCharacter(character.get(0))
        val actual = localDataSource.localGetAllCharacterSync()
        assertThat(character).isEqualTo(actual.data)
    }
}