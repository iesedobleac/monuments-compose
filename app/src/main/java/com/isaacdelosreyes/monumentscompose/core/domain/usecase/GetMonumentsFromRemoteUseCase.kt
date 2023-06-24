package com.isaacdelosreyes.monumentscompose.core.domain.usecase

import com.isaacdelosreyes.monumentscompose.core.data.model.monument.MonumentsDto
import com.isaacdelosreyes.monumentscompose.core.data.repository.MonumentsRepositoryImpl
import javax.inject.Inject

class GetMonumentsFromRemoteUseCase @Inject constructor(
    private val repository: MonumentsRepositoryImpl
) {

    suspend operator fun invoke(): MonumentsDto {
        return repository.getAllMonumentsFromRemote()
    }
}