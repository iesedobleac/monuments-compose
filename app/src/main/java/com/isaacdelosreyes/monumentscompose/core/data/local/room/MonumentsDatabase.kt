package com.isaacdelosreyes.monumentscompose.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.isaacdelosreyes.monumentscompose.core.data.local.converter.CoordinatesTypeConverter
import com.isaacdelosreyes.monumentscompose.core.data.local.entities.MonumentEntity

@TypeConverters(CoordinatesTypeConverter::class)
@Database(entities = [MonumentEntity::class], version = 1)
abstract class MonumentsDatabase: RoomDatabase() {

    abstract fun getMonumentsDao(): MonumentsDao
}