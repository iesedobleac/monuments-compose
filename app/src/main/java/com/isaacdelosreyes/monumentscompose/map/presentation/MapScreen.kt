package com.isaacdelosreyes.monumentscompose.map.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.isaacdelosreyes.monumentscompose.core.data.mapper.toLatLng
import kotlinx.coroutines.launch

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    navigateToDetailScreen: (String) -> Unit
) {
    val state by viewModel.state

    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()

    GoogleMap(
        cameraPositionState = cameraPositionState
    ) {
        val builder = LatLngBounds.builder()

        state.monuments.forEach {
            Marker(
                state = MarkerState(position = it.location.toLatLng()),
                title = it.title,
                snippet = "Marker in ${it.title}",
                onInfoWindowClick = { marker ->
                    coroutineScope.launch {
                        navigateToDetailScreen(
                            marker.title.orEmpty()
                        )
                    }
                }
            )
            builder.include(it.location.toLatLng())
        }

        val camera = CameraUpdateFactory.newLatLngBounds(
            builder.build(),
            200
        )
        cameraPositionState.move(camera)
    }
}