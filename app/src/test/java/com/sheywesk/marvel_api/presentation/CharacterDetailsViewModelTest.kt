package com.sheywesk.marvel_api.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.sheywesk.marvel_api.data.models.Character
import com.sheywesk.marvel_api.data.models.Image
import com.sheywesk.marvel_api.data.repository.FakeCharacterRepository
import com.sheywesk.marvel_api.presentation.character_details.CharacterDetailsViewModel
import com.sheywesk.marvel_api.presentation.home.CharacterViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterDetailsViewModelTest {
    private lateinit var characterDetailsViewModel:CharacterDetailsViewModel
    private lateinit var repository: FakeCharacterRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        repository = FakeCharacterRepository()
        val character = Character(
            0, "test1",
            "test1",
            false,
            Image("teste1", "teste1")
        )
        repository.addCharacterToTest(character)
        characterDetailsViewModel = CharacterDetailsViewModel(FakeCharacterRepository())
    }

    @Test
    fun `update favorite,return success`() = runBlockingTest {
        val character = repository.findCharacterById(0)
        character.data?.favorite = true
        repository.updateFavorite(character.data!!)

        val expect = Character(
            0, "test1",
            "test1",
            true,
            Image("teste1", "teste1"))
        Truth.assertThat(expect).isEqualTo(character.data)
    }
}