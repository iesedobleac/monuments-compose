package com.isaacdelosreyes.monumentscompose.favorites.presentation

import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument

data class FavoriteState(
    val monuments: List<Monument> = emptyList()
)
