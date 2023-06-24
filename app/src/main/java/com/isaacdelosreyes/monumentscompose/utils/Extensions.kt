package com.isaacdelosreyes.monumentscompose.utils

import android.content.Context
import android.location.Geocoder
import com.murgupluoglu.flagkit.FlagKit
import java.util.Locale

@Suppress("DEPRECATION")
fun Context.getCountryCodeFromCoordinates(
    latitude: Double,
    longitude: Double
): Int? {
    val geoCoder = Geocoder(this, Locale.getDefault())
    val address = geoCoder.getFromLocation(
        latitude,
        longitude,
        3
    )
    return FlagKit.getResId(
        context = this,
        flagName = address?.firstOrNull()?.countryCode.toString()
    )
}