package com.sheywesk.marvel_api.data.models

import android.content.Context
import androidx.room.Room
import org.junit.Before
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import com.google.common.truth.Truth.assertThat
import com.sheywesk.marvel_api.data.datasource.local.CharacterDao
import com.sheywesk.marvel_api.data.datasource.local.CharacterDatabase
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class CharacterDataBaseTest {
    private lateinit var characterDao: CharacterDao
    private lateinit var databaseTest: CharacterDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        databaseTest = Room.inMemoryDatabaseBuilder(
            context, CharacterDatabase::class.java
        ).build()
        characterDao = databaseTest.characterDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        databaseTest.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadCharacter() = runBlocking {
        val character = Character(
            0,
            "homem de ferro",
            "O homem com mais ferro",
            false,
            Image("adadad", "jpg"),

        )

        characterDao.save(character)
        val byId = characterDao.findCharacterById(0)
        assertThat(byId).isEqualTo(character)

    }
}