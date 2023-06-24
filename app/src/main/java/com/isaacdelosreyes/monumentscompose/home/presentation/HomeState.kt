package com.isaacdelosreyes.monumentscompose.home.presentation

import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument

data class HomeState(
    val monuments: List<Monument> = emptyList()
)