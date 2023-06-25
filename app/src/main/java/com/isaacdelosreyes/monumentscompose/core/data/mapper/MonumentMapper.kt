package com.isaacdelosreyes.monumentscompose.core.data.mapper

import com.isaacdelosreyes.monumentscompose.core.data.local.entities.MonumentEntity
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.MonumentDto

fun Monument.toEntity() = MonumentEntity(
    id = id,
    title = title,
    location = location.toEntity(),
    description = description,
    image = image,
    author = author,
    isFavorite = isFavorite
)

fun MonumentEntity.toDomain() = Monument(
    id = id,
    title = title,
    location = location.toDomain(),
    description = description,
    image = image,
    author = author,
    isFavorite = isFavorite
)

fun MonumentDto.toDomain() =
    Monument(
        title = title,
        location = location.toDomain(),
        description = description,
        image = image,
        author = author
    )