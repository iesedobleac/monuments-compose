package com.isaacdelosreyes.monumentscompose.favorites.domain.usecase

import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.core.data.repository.MonumentsRepositoryImpl
import javax.inject.Inject

class InsertFavoriteMonumentUseCase @Inject constructor(
    private val repository: MonumentsRepositoryImpl
) {

    suspend operator fun invoke(monument: Monument) {
        repository.insertMonumentFavorite(monument = monument)
    }
}