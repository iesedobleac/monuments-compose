package com.isaacdelosreyes.monumentscompose.core.data.model.monument

import com.isaacdelosreyes.monumentscompose.core.data.model.coordinates.CoordinatesDto

data class MonumentsDto(
    val monuments: List<MonumentDto>
)

data class MonumentDto(
    val title: String,
    val location: CoordinatesDto,
    val description: String,
    val image: String,
    val author: String
)




