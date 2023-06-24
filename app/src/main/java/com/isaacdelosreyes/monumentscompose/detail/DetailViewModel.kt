package com.isaacdelosreyes.monumentscompose.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaacdelosreyes.monumentscompose.core.domain.usecase.GetMonumentsFromLocalUseCase
import com.isaacdelosreyes.monumentscompose.utils.MONUMENT_TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMonumentsFromLocalUseCase: GetMonumentsFromLocalUseCase
) : ViewModel() {

    var monumentState = mutableStateOf(DetailState())
        private set

    private val monumentTitle: String? = savedStateHandle[MONUMENT_TITLE]

    init {
        getMonument()
    }

    private fun getMonument() {
        viewModelScope.launch {
            getMonumentsFromLocalUseCase().collectLatest {
                val monument = it.find { monument ->
                    monument.title == monumentTitle
                }
                monumentState.value = monumentState.value.copy(
                    monument = monument
                )
            }
        }
    }
}