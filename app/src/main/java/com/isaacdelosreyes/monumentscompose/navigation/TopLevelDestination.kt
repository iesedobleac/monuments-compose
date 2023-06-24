package com.isaacdelosreyes.monumentscompose.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector
import com.isaacdelosreyes.monumentscompose.R
import com.isaacdelosreyes.monumentscompose.utils.Routes

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val route: String,
    @StringRes val titleTextId: Int,
) {
    HOME(
        selectedIcon = Icons.Default.Home,
        route = Routes.Home.route,
        titleTextId = R.string.top_app_bar_home_title,
    ),
    MAPS(
        selectedIcon = Icons.Default.Map,
        route = Routes.Map.route,
        titleTextId = R.string.top_app_bar_maps_title,
    ),
    FAVORITES(
        selectedIcon = Icons.Default.Favorite,
        route = Routes.Favorites.route,
        titleTextId = R.string.top_app_bar_fav_title,
    ),
}