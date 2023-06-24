package com.isaacdelosreyes.monumentscompose.core.data.model.monument

import com.isaacdelosreyes.monumentscompose.core.data.model.coordinates.Coordinates

data class Monument(
    val id: Int = 0,
    val title: String,
    val location: Coordinates,
    val description: String,
    val image: String,
    val author: String,
    val isFavorite: Boolean = false
)

fun getFakeMonument() = Monument(
    id = 0,
    title = "",
    location = Coordinates(
        latitude = 0.0f,
        longitude = 0.0f
    ),
    description = "",
    image = "",
    author = "",
    isFavorite = false
)