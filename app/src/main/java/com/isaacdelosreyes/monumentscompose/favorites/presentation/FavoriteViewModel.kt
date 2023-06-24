package com.isaacdelosreyes.monumentscompose.favorites.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.favorites.domain.usecase.GetFavoriteMonumentsUseCase
import com.isaacdelosreyes.monumentscompose.favorites.domain.usecase.InsertFavoriteMonumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val insertFavoriteMonumentToDatabaseUseCase: InsertFavoriteMonumentUseCase,
    private val getFavoriteMonumentUseCase: GetFavoriteMonumentsUseCase
) : ViewModel() {

    var favoritesState = mutableStateOf(FavoriteState())
        private set

    init {
        getFavoriteMonuments()
    }

    private fun getFavoriteMonuments() {
        viewModelScope.launch {
            getFavoriteMonumentUseCase().collectLatest {
                favoritesState.value = favoritesState.value.copy(
                    monuments = it
                )
            }
        }
    }

    fun setMonumentFavorite(monument: Monument) {
        viewModelScope.launch {
            insertFavoriteMonumentToDatabaseUseCase(
                monument.copy(isFavorite = !monument.isFavorite)
            )
        }
    }
}