package com.sheywesk.marvel_api.di

import android.app.Application
import androidx.room.Room
import com.sheywesk.marvel_api.data.api.MarvelService
import com.sheywesk.marvel_api.data.datasource.remote.RemoteDatasource
import com.sheywesk.marvel_api.data.datasource.local.CharacterDao
import com.sheywesk.marvel_api.data.datasource.local.CharacterDatabase
import com.sheywesk.marvel_api.data.datasource.local.ILocalDataSource
import com.sheywesk.marvel_api.data.datasource.local.LocalDatasource
import com.sheywesk.marvel_api.data.datasource.remote.IRemoteDataSource
import com.sheywesk.marvel_api.data.repository.CharacterRepository
import com.sheywesk.marvel_api.data.repository.ICharacterRepository
import com.sheywesk.marvel_api.presentation.home.CharacterViewModel
import com.sheywesk.marvel_api.presentation.character_details.CharacterDetailsViewModel
import com.sheywesk.marvel_api.presentation.search_fragment.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val databaseModules = module {

    fun provideDatabase(application: Application): CharacterDatabase {
        return Room.databaseBuilder(
            application,
            CharacterDatabase::class.java,
            "character_table"
        ).build()
    }

    fun provideCharacterDao(database: CharacterDatabase): CharacterDao {
        return database.characterDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCharacterDao(get()) }
}
val dataModules = module {
    single { MarvelService() }
    single <ILocalDataSource>{ LocalDatasource(get(),androidApplication()) }
    single <IRemoteDataSource>{ RemoteDatasource(get(),androidApplication()) }
    single <ICharacterRepository> { CharacterRepository(get(),androidApplication(),get()) }
}
val presentationModule = module {
    viewModel { CharacterViewModel(get()) }
    viewModel { CharacterDetailsViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}