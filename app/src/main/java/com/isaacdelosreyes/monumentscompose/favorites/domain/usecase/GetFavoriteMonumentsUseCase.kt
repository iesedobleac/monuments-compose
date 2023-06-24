package com.isaacdelosreyes.monumentscompose.favorites.domain.usecase

import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.core.data.repository.MonumentsRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMonumentsUseCase @Inject constructor(
    private val repository: MonumentsRepositoryImpl
) {

    suspend operator fun invoke(): Flow<List<Monument>> {
        return repository.getAllFavoriteMonuments()
    }
}