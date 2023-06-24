package com.isaacdelosreyes.monumentscompose.home.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaacdelosreyes.monumentscompose.core.data.mapper.toDomain
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.core.domain.usecase.GetMonumentsFromLocalUseCase
import com.isaacdelosreyes.monumentscompose.core.domain.usecase.GetMonumentsFromRemoteUseCase
import com.isaacdelosreyes.monumentscompose.core.domain.usecase.InsertMonumentsUseCase
import com.isaacdelosreyes.monumentscompose.favorites.domain.usecase.InsertFavoriteMonumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertMonumentsUseCase: InsertMonumentsUseCase,
    private val insertFavoriteMonumentToDatabaseUseCase: InsertFavoriteMonumentUseCase,
    private val getMonumentsFromLocalUseCase: GetMonumentsFromLocalUseCase,
    private val getMonumentsFromRemoteUseCase: GetMonumentsFromRemoteUseCase
) : ViewModel() {

    var monumentsState = mutableStateOf(HomeState())
        private set

    init {
        getMonuments()
    }

    private suspend fun getMonumentsFromLocal() {
        getMonumentsFromLocalUseCase().collectLatest {
            monumentsState.value = monumentsState.value.copy(
                monuments = it
            )
        }
    }

    private fun getMonuments() {
        viewModelScope.launch(Dispatchers.IO) {
            getMonumentsFromLocalUseCase().collectLatest { monuments ->
                monuments.map { it }.takeIf { it.isNotEmpty() }?.apply {
                    monumentsState.value = monumentsState
                        .value.copy(
                            monuments = this
                        )
                } ?: run {
                    insertMonumentsUseCase(monuments =
                    getMonumentsFromRemoteUseCase()
                        .monuments.map { it.toDomain() }
                    )
                    getMonumentsFromLocal()
                }
            }
        }
    }

    fun setMonumentFavorite(monument: Monument) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteMonumentToDatabaseUseCase(
                monument.copy(isFavorite = !monument.isFavorite)
            )
            getMonumentsFromLocal()
        }
    }
}