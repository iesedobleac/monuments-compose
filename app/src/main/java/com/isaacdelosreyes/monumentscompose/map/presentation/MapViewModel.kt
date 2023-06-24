package com.isaacdelosreyes.monumentscompose.map.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaacdelosreyes.monumentscompose.core.domain.usecase.GetMonumentsFromLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMonumentsFromLocalUseCase: GetMonumentsFromLocalUseCase
) : ViewModel() {

    var state = mutableStateOf(MapState())
        private set

    init {
        getCoordinatesFromMonuments()
    }

    private fun getCoordinatesFromMonuments() {
        viewModelScope.launch {
            getMonumentsFromLocalUseCase().collectLatest {
                state.value = state.value.copy(
                    monuments = it
                )
            }
        }
    }
}