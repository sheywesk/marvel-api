package com.sheywesk.marvel_api.data.datasource.local

import androidx.room.*
import com.sheywesk.marvel_api.data.models.Character

//@TypeConverters(Converter::class)
@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}