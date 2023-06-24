package com.isaacdelosreyes.monumentscompose.formulary.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.isaacdelosreyes.monumentscompose.core.data.model.coordinates.Coordinates
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.formulary.domain.usecase.InsertMonumentUseCase
import com.isaacdelosreyes.monumentscompose.utils.PHOTOS_DIR
import com.isaacdelosreyes.monumentscompose.utils.PNG_FILE_FULL_QUALITY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class CreateMonumentViewModel @Inject constructor(
    private val insertMonumentUseCase: InsertMonumentUseCase
) : ViewModel() {

    var state = mutableStateOf(CreateMonumentState())
        private set

    fun updateName(name: String) {
        state.value = state.value.copy(
            name = name
        )
    }

    fun updateDescription(description: String) {
        state.value = state.value.copy(
            description = description
        )
    }

    fun updateAuthor(author: String) {
        state.value = state.value.copy(
            author = author
        )
    }

    fun updateImageUri(newUri: Uri?, context: Context) {
        viewModelScope.launch {
            state.value = state.value.copy(
                imageUri = newUri
            )
            state.value.imageUri?.let {
                setBitmapValue(it, context)
            }
        }
    }

    fun disableScrollWhenMapIsDragging(isMoving: Boolean) {
        viewModelScope.launch {
            state.value = state.value.copy(
                columnScrollingEnabled = isMoving
            )
        }
    }

    fun updateLastPositionPressed(newLatLng: LatLng) {
        viewModelScope.launch {
            state.value = state.value.copy(
                lastLocationPressed = newLatLng
            )
        }
    }

    fun checkFormularyFields() = state.value.name.isNotEmpty()
            && state.value.description.isNotEmpty()
            && state.value.author.isNotEmpty()

    fun saveMonumentInDatabase(
        context: Context
    ) {
        val monumentNameFilter = state.value.name
            .lowercase()
            .trim()
            .replace(" ", "")

        val urlImage = "${
            getImageUrlFromExternalStorage(
                context
            )
        }/$monumentNameFilter.png"

        val coordinates = Coordinates(
            latitude = state.value
                .lastLocationPressed
                ?.latitude?.toFloat() ?: 0.0f,
            longitude = state.value
                .lastLocationPressed
                ?.longitude?.toFloat() ?: 0.0f,
        )

        val monument = Monument(
            title = state.value.name,
            description = state.value.description,
            author = state.value.author,
            image = urlImage,
            location = coordinates
        )

        viewModelScope.launch(Dispatchers.IO) {
            saveImageToExternalStorage(
                imageUri = state.value.imageUri,
                context = context,
                title = monumentNameFilter
            )
            insertMonumentUseCase(monument)
        }
    }

    @Suppress("DEPRECATION")
    private fun setBitmapValue(imageUri: Uri, context: Context) {
        viewModelScope.launch {
            state.value = state.value.copy(
                bitmap = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    MediaStore.Images.Media.getBitmap(
                        context.contentResolver,
                        imageUri
                    )

                } else {
                    val source = ImageDecoder.createSource(
                        context.contentResolver,
                        imageUri
                    )
                    ImageDecoder.decodeBitmap(source)
                }
            )
        }
    }

    private fun saveImageToExternalStorage(
        imageUri: Uri?,
        context: Context,
        title: String
    ) {
        val externalStorage = context.getExternalFilesDir(PHOTOS_DIR)
        val file = File(externalStorage?.absolutePath.orEmpty(), "$title.png")
        val outputStream = FileOutputStream(file)
        imageUri?.let { setBitmapValue(imageUri = it, context = context) }
        state.value.bitmap?.compress(
            Bitmap.CompressFormat.PNG,
            PNG_FILE_FULL_QUALITY,
            outputStream
        )
        outputStream.close()
    }

    private fun getImageUrlFromExternalStorage(context: Context) =
        context.getExternalFilesDir(PHOTOS_DIR)?.absolutePath
}