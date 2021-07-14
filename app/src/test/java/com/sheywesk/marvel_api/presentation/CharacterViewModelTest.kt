package com.sheywesk.marvel_api.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sheywesk.marvel_api.data.repository.FakeCharacterRepository
import com.sheywesk.marvel_api.presentation.home.CharacterViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterViewModelTest {
    private lateinit var characterViewModel: CharacterViewModel
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        characterViewModel = CharacterViewModel(FakeCharacterRepository())
    }

    fun `request all character,return success`(){
        val character = characterViewModel.marvelViewModel.value

    }

}