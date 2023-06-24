package com.isaacdelosreyes.monumentscompose.core.domain.usecase

import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.core.data.repository.MonumentsRepositoryImpl
import javax.inject.Inject

class InsertMonumentsUseCase @Inject constructor(
    private val repository: MonumentsRepositoryImpl
) {

    suspend operator fun invoke(monuments: List<Monument>) {
        repository.insertAllMonuments(monuments = monuments)
    }
}