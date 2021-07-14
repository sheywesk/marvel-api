package com.sheywesk.marvel_api.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sheywesk.marvel_api.data.models.Character


@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(character: Character)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(character: Character)

    @Query("SELECT * FROM character_table")
    fun getAllCharacter(): LiveData<List<Character>>

    @Query("SELECT * FROM character_table")
    suspend fun getAllCharacterSync(): List<Character>

    @Query("SELECT * FROM character_table WHERE id = :id")
    suspend fun findCharacterById(id: Int): Character?

    @Query("SELECT COUNT(id) FROM character_table")
    suspend fun checkIsNull(): Int
}