package com.isaacdelosreyes.monumentscompose.formulary.presentation

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.isaacdelosreyes.monumentscompose.R

@Composable
fun CreateMonumentScreen(
    viewModel: CreateMonumentViewModel = hiltViewModel(),
    goToHomeFragment: () -> Unit
) {

    val state by viewModel.state

    val rememberVerticalScroll = rememberScrollState()
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(0.0, 0.0), 1F
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = rememberVerticalScroll,
                enabled = state.columnScrollingEnabled
            )
    ) {

        LaunchedEffect(
            cameraPositionState.isMoving,
            state.lastLocationPressed?.latitude
        ) {
            viewModel.disableScrollWhenMapIsDragging(
                !cameraPositionState.isMoving
            )
        }
        PersonalData(
            viewModel = viewModel
        )
        LocationMaps(
            cameraPositionState = cameraPositionState,
            viewModel = viewModel,
            createMonumentState = state
        )
        AddImages(
            context = context,
            viewModel = viewModel,
        )
        Button(
            onClick = {
                if (viewModel.checkFormularyFields()) {
                    viewModel.saveMonumentInDatabase(
                        context = context
                    )
                    goToHomeFragment()
                }

            }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(text = "Guardar")
        }
    }
}

@Composable
private fun AddImages(
    context: Context,
    viewModel: CreateMonumentViewModel
) {
    val state by viewModel.state

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        activityResult.data?.data?.let {
            viewModel.updateImageUri(
                newUri = it,
                context = context
            )
        }
    }

    Text(
        text = "Añádele imágenes",
        fontSize = 18.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
    )
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 80.dp, height = 80.dp)
                .clickable {
                    if (state.name.isNotEmpty()) {
                        val intent = Intent(
                            Intent.ACTION_OPEN_DOCUMENT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                            .apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                            }
                        launcher.launch(intent)

                    } else {
                        Toast
                            .makeText(
                                context,
                                "Primero rellena el nombre",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
        )
        state.bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(height = 80.dp, width = 80.dp)
            )
        }
    }
}


@Composable
fun CreateMonumentTextField(
    value: String,
    modifier: Modifier = Modifier,
    height: Dp = TextFieldDefaults.MinHeight,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    labelText: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value, onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(
                text = labelText,
                color = Color.Gray,
                fontSize = 14.sp
            )
        },
        maxLines = maxLines,
        singleLine = singleLine,
        modifier = modifier
            .defaultMinSize(minHeight = 70.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .height(height)
    )
}

@Composable
fun PersonalData(
    viewModel: CreateMonumentViewModel
) {
    Text(
        text = "Añade un monumento",
        fontSize = 24.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
    )
    CreateMonumentTextField(
        value = viewModel.state.value.name,
        labelText = "Nombre",
        onValueChange = {
            viewModel.updateName(it)
        }
    )
    CreateMonumentTextField(
        value = viewModel.state.value.description,
        labelText = "Descripción",
        maxLines = 10,
        singleLine = false,
        height = 200.dp,
        onValueChange = {
            viewModel.updateDescription(it)
        }
    )
    CreateMonumentTextField(
        value = viewModel.state.value.author,
        labelText = "Autor",
        onValueChange = {
            viewModel.updateAuthor(it)
        }
    )
}

@Composable
fun LocationMaps(
    createMonumentState: CreateMonumentState,
    cameraPositionState: CameraPositionState,
    viewModel: CreateMonumentViewModel
) {
    Text(
        text = "Búscalo en el mapa",
        fontSize = 18.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
    )
    MapInsideAColumn(cameraPositionState, onMapClicked = {
        viewModel.updateLastPositionPressed(it)
    }, onMapTouched = {
        viewModel.disableScrollWhenMapIsDragging(false)
    }) {
        createMonumentState.lastLocationPressed?.let {
            Marker(state = MarkerState(it))
        }
    }
}


@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun MapInsideAColumn(
    cameraPositionState: CameraPositionState,
    onMapClicked: (LatLng) -> Unit,
    onMapTouched: () -> Unit,
    content: @Composable () -> Unit,
) {
    GoogleMap(
        cameraPositionState = cameraPositionState,
        onMapClick = {
            onMapClicked(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        onMapTouched()
                        false
                    }

                    else -> true
                }
            }
    ) {
        content()
    }
}




