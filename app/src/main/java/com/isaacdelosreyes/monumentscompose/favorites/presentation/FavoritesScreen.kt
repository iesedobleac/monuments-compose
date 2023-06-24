package com.isaacdelosreyes.monumentscompose.favorites.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.isaacdelosreyes.monumentscompose.R
import com.isaacdelosreyes.monumentscompose.core.data.model.monument.Monument
import com.isaacdelosreyes.monumentscompose.ui.theme.LightBlue
import com.isaacdelosreyes.monumentscompose.utils.getCountryCodeFromCoordinates

@Composable
fun FavoritesScreen(viewModel: FavoriteViewModel = hiltViewModel()) {

    val state by viewModel.favoritesState

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(LightBlue), content = {
        if (state.monuments.isNotEmpty()) {
            items(state.monuments) {
                MonumentRow(monument = it, clickOnFavoriteButton = {
                    viewModel.setMonumentFavorite(it)
                })
            }

        } else {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillParentMaxSize()
                ) {
                    Text(text = "No hay ningún monumento favorito")
                }
            }
        }
    })
}

@Composable
private fun MonumentRow(monument: Monument, clickOnFavoriteButton: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = monument.image,
            contentDescription = stringResource(
                id = R.string.home_monument_main_image_content_description
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(130.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Column(Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = monument.title, fontSize = 16.sp
                )

                val countryCode = LocalContext.current
                    .getCountryCodeFromCoordinates(
                        monument.location.latitude.toDouble(),
                        monument.location.longitude.toDouble()
                    )

                if (countryCode != null && countryCode > 0) {
                    Image(
                        painter = painterResource(
                            id = countryCode
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        clickOnFavoriteButton()
                    }
                ) {
                    Icon(
                        imageVector = if (monument.isFavorite) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = ""
                    )
                }
            }
            Text(
                text = monument.description,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 10.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                lineHeight = 20.sp
            )
            Text(
                text = monument.author,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 10.dp),
                color = Color.LightGray,
            )
        }
    }
}