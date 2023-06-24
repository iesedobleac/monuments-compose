package com.isaacdelosreyes.monumentscompose.formulary.presentation

import android.graphics.Bitmap
import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class CreateMonumentState(
    val columnScrollingEnabled: Boolean = false,
    val name: String = "",
    val description: String = "",
    val author: String = "",
    val imageUri: Uri? = null,
    val bitmap: Bitmap? = null,
    val lastLocationPressed: LatLng? = null
)