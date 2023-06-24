package com.isaacdelosreyes.monumentscompose.formulary.domain.usecase

import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.core.data.repository.MonumentsRepositoryImpl
import javax.inject.Inject

class InsertMonumentUseCase @Inject constructor(
    private val repository: MonumentsRepositoryImpl
) {

    suspend operator fun invoke(monument: Monument) {
        repository.insertMonument(monument = monument)
    }
}