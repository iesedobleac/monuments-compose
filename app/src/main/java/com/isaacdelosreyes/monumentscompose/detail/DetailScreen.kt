package com.isaacdelosreyes.monumentscompose.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.getFakeMonument

/**
 * Si queremos crear una preview de nuestra pantalla pero nos encontramos que no puede renderizar
 * la instancia del viewModel, debemos pasarle esos datos que recibimos del viewmodel a través de
 * parámetros en una función composable. No serán datos reales pero nos servirá al menos para ver
 * la preview.
 **/

@Preview(showSystemUi = true)
@Composable
fun DetailScreenPreview(
    monuments: Monument = getFakeMonument(),
    onBackPressed: () -> Unit = {}
) {

    DetailBody(monument = monuments)

    IconButton(
        onClick = { onBackPressed() },
        modifier = Modifier.padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowCircleLeft,
            tint = Color.White,
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {

    val state by viewModel.monumentState

    DetailBody(monument = state.monument)

    IconButton(
        onClick = { onBackPressed() },
        modifier = Modifier.padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowCircleLeft,
            tint = Color.White,
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
private fun DetailBody(monument: Monument?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = monument?.image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
        ) {
            Text(
                text = monument?.title.orEmpty(),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Icon(
                imageVector = if (monument?.isFavorite == true) {
                    Icons.Default.Favorite

                } else {
                    Icons.Default.FavoriteBorder
                }, contentDescription = ""
            )
        }

        Text(
            text = monument?.author.orEmpty(),
            fontSize = 14.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 20.dp, top = 5.dp)
        )
        Text(
            text = monument?.description.orEmpty(),
            fontSize = 14.sp,
            modifier = Modifier.padding(20.dp),
            maxLines = 10,
            overflow = TextOverflow.Ellipsis
        )
    }
}
