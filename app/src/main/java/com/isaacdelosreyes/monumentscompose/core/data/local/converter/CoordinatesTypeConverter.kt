package com.isaacdelosreyes.monumentscompose.core.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.isaacdelosreyes.monumentscompose.core.data.local.entities.CoordinatesEntity

class CoordinatesTypeConverter {

    @TypeConverter
    fun fromDeveloperList(coordinates: CoordinatesEntity?): String? {
        val type = object : TypeToken<CoordinatesEntity>() {}.type
        return Gson().toJson(coordinates, type)
    }

    @TypeConverter
    fun toDeveloperList(coordinates: String?): CoordinatesEntity? {
        val type = object : TypeToken<CoordinatesEntity>() {}.type
        return Gson().fromJson<CoordinatesEntity>(coordinates, type)
    }
}