package com.isaacdelosreyes.monumentscompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.isaacdelosreyes.monumentscompose.navigation.TopLevelDestination

@Composable
fun rememberMonumentsState(
    navController: NavHostController = rememberNavController()
): MonumentsState {

    return remember(
        navController
    ) {
        MonumentsState(
            navController = navController
        )
    }
}

class MonumentsState(
    val navController: NavHostController,
) {

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value?.destination

    val shouldShowAppBar: Boolean
        @Composable get() = when (currentDestination?.route) {
            Routes.Home.route,
            Routes.Map.route,
            Routes.Favorites.route,
            Routes.Detail.route -> true

            else -> false
        }

    val shouldShowFloatingButton: Boolean
        @Composable get() = when (currentDestination?.route) {
            Routes.Home.route -> true
            Routes.Map.route,
            Routes.Favorites.route -> false

            else -> false
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            Routes.Home.route -> TopLevelDestination.HOME
            Routes.Map.route -> TopLevelDestination.MAPS
            Routes.Favorites.route -> TopLevelDestination.FAVORITES
            else -> null
        }
}