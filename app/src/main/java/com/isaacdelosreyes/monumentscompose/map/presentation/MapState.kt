package com.isaacdelosreyes.monumentscompose.map.presentation

import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument

data class MapState(
    val monuments: List<Monument> = emptyList()
)
