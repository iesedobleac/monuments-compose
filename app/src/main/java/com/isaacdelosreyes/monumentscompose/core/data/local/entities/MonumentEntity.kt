package com.isaacdelosreyes.monumentscompose.core.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Monuments")
data class MonumentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @Embedded
    val location: CoordinatesEntity,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "favorite")
    val isFavorite: Boolean = false
)

