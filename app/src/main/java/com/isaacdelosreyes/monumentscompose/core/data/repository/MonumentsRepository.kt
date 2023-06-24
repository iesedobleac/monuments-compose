package com.isaacdelosreyes.monumentscompose.core.data.repository

import com.isaacdelosreyes.monumentscompose.core.data.local.room.MonumentsDao
import com.isaacdelosreyes.monumentscompose.core.data.mapper.toDomain
import com.isaacdelosreyes.monumentscompose.core.data.mapper.toEntity
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.MonumentsDto
import com.isaacdelosreyes.monumentscompose.core.data.remote.retrofit.MonumentWs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface MonumentsRepository {

    suspend fun getAllMonumentsFromRemote(): MonumentsDto
    suspend fun getAllMonumentsFromLocal(): Flow<List<Monument>>
    suspend fun getAllFavoriteMonuments(): Flow<List<Monument>>
    suspend fun insertAllMonuments(monuments: List<Monument>)
    suspend fun insertMonument(monument: Monument)
    suspend fun insertMonumentFavorite(monument: Monument)
}

class MonumentsRepositoryImpl @Inject constructor(
    private val monumentsDao: MonumentsDao,
    private val monumentWs: MonumentWs
) : MonumentsRepository {

    override suspend fun insertAllMonuments(monuments: List<Monument>) {
        monumentsDao.insertAllMonuments(
            monuments.map { it.toEntity() }
        )
    }

    override suspend fun insertMonument(monument: Monument) {
        monumentsDao.insertMonument(monument.toEntity())
    }

    override suspend fun insertMonumentFavorite(monument: Monument) {
        monumentsDao.insertMonumentFavorite(
            monument.toEntity()
        )
    }

    override suspend fun getAllMonumentsFromRemote() =
        monumentWs.getAllMonuments()

    override suspend fun getAllMonumentsFromLocal() =
        monumentsDao.getMonuments().map {
            it.map { monumentEntity ->
                monumentEntity.toDomain()
            }
        }

    override suspend fun getAllFavoriteMonuments() =
        monumentsDao.getFavoriteMonuments().map {
            it.map { monumentEntity ->
                monumentEntity.toDomain()
            }
        }

}
