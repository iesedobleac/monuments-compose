package com.isaacdelosreyes.monumentscompose.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.isaacdelosreyes.monumentscompose.core.data.local.entities.MonumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonumentsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMonument(monumentEntity: MonumentEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMonuments(monuments: List<MonumentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonumentFavorite(monumentEntity: MonumentEntity)

    @Query("SELECT * FROM Monuments")
    fun getMonuments(): Flow<List<MonumentEntity>>

    @Query("SELECT * FROM Monuments WHERE favorite = 1")
    fun getFavoriteMonuments(): Flow<List<MonumentEntity>>
}