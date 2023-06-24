package com.isaacdelosreyes.monumentscompose.core.data.mapper

import com.google.android.gms.maps.model.LatLng
import com.isaacdelosreyes.monumentscompose.core.data.local.entities.CoordinatesEntity
import com.isaacdelosreyes.monumentscompose.core.data.model.coordinates.Coordinates
import com.isaacdelosreyes.monumentscompose.core.data.model.coordinates.CoordinatesDto

fun CoordinatesDto.toDomain() =
    Coordinates(
        longitude = longitude,
        latitude = latitude
    )

fun CoordinatesEntity.toDomain() =
    Coordinates(
        longitude = longitude,
        latitude = latitude
    )

fun Coordinates.toEntity() =
    CoordinatesEntity(
        longitude = longitude,
        latitude = latitude
    )

fun Coordinates.toLatLng() =
    LatLng(
        latitude.toDouble(),
        longitude.toDouble()
    )
