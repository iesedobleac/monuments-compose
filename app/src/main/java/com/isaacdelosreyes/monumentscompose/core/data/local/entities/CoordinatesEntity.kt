package com.isaacdelosreyes.monumentscompose.core.data.local.entities

import androidx.room.PrimaryKey
import com.isaacdelosreyes.monumentscompose.core.data.model.coordinates.Coordinates
import com.isaacdelosreyes.monumentscompose.core.data.model.coordinates.CoordinatesDto

data class CoordinatesEntity(
    @PrimaryKey(autoGenerate = true)
    val monumentId: Int = 0,
    val latitude: Float,
    val longitude: Float
)