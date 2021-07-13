package com.sheywesk.marvel_api.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sheywesk.marvel_api.data.getOrAwaitValueTest
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.models.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()
    private val character1 = Character(
        1,
        "Teste 1",
        "teste character 1",
        false,
        Image("teste", "jpg"),
    )
    private val character2 = Character(
        2,
        "Teste 2",
        "teste character 2",
        false,
        Image("teste", "jpg"),
    )
    private val character3 = Character(
        3,
        "Teste 3",
        "teste character 3",
        false,
        Image("teste", "jpg"),
    )
    private val remoteCharacter = listOf(character1, character2)
    private val localCharacter = listOf(character3)


    private lateinit var characterRemoteDataSource: FakeDataSource
    private lateinit var characterLocalDataSource: FakeDataSource

    private lateinit var characterRepository: CharacterRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Before
    fun createRepository() {
        characterLocalDataSource = FakeDataSource(localCharacter.toMutableList())
        characterRemoteDataSource = FakeDataSource(remoteCharacter.toMutableList())
        characterRepository =
            CharacterRepository(characterRemoteDataSource, characterLocalDataSource)
    }

    @Test
    fun `request all character from datasource,return success`() {
        val expect = Result.success(listOf(character3, character1, character2))
        val character = characterRepository.getAllCharacter().getOrAwaitValueTest()
        assertThat(character).isEqualTo(expect)
    }

    @Test
    fun `request all character from local datasource without connection,return success`() {
        characterRemoteDataSource.setShouldReturnNetworkStatus(false)
        val expect = Result.success(localCharacter)
        val character = characterRepository.getAllCharacter().getOrAwaitValueTest()
        assertThat(character).isEqualTo(expect)
    }

}