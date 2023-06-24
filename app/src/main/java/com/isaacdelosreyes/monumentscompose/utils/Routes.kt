package com.isaacdelosreyes.monumentscompose.utils

sealed class Routes(val route: String) {

    object Launcher : Routes("launcher")
    object Home : Routes("home")

    object Detail : Routes("detail") {

        fun createRoute(monumentTitle: String) =
            "$route/$monumentTitle"
    }

    object Map : Routes("map")
    object Favorites : Routes("favorites")
    object Formulary : Routes("formulary")
}