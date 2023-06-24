package com.isaacdelosreyes.monumentscompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.isaacdelosreyes.monumentscompose.detail.DetailScreen
import com.isaacdelosreyes.monumentscompose.favorites.presentation.FavoritesScreen
import com.isaacdelosreyes.monumentscompose.formulary.presentation.CreateMonumentScreen
import com.isaacdelosreyes.monumentscompose.home.presentation.HomeScreen
import com.isaacdelosreyes.monumentscompose.launcher.presentation.LauncherScreen
import com.isaacdelosreyes.monumentscompose.map.presentation.MapScreen
import com.isaacdelosreyes.monumentscompose.utils.MONUMENT_TITLE
import com.isaacdelosreyes.monumentscompose.utils.Routes

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Launcher.route,
        modifier = modifier
    ) {
        composable(Routes.Launcher.route) {
            LauncherScreen {
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Launcher.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(Routes.Home.route) {
            HomeScreen() {
                navController.navigate(
                    route = Routes.Detail.createRoute(it)
                )
            }
        }
        composable(Routes.Favorites.route) {
            FavoritesScreen()
        }
        composable(Routes.Map.route) {
            MapScreen {
                navController.navigate(
                    route = Routes.Detail.createRoute(it)
                )
            }
        }
        composable(
            route = "${Routes.Detail.route}/{${NavArgs.MonumentTitle.key}}",
            arguments = listOf(navArgument(NavArgs.MonumentTitle.key) {
                type = NavType.StringType
            })
        ) {
            DetailScreen() {
                navController.popBackStack()
            }
        }
        composable(Routes.Formulary.route) {
            CreateMonumentScreen {
                navController.navigate(Routes.Home.route)
            }
        }
    }
}

enum class NavArgs(val key: String) {
    MonumentTitle(MONUMENT_TITLE)
}