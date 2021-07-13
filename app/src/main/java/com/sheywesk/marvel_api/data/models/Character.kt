package com.sheywesk.marvel_api.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class Character(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,
    @Embedded
    val thumbnail: Image,
)

data class CharacterList(
    val results: List<Character>
)

data class CharacterResult(
    val data: CharacterList
)


data class Image(
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "extension") val extension: String
)