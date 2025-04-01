package com.example.kueskichagenge.ui.home.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.kueskichagenge.core.extensions.empty

sealed class HomeRoute(val route: String) {
    data object Movies : HomeRoute("movies")
    data object MovieDetail : HomeRoute("movieDetail/{$MOVIE_ID_ARGUMENT}"){

        fun createRoute(movieId: String): String {
            return route.replace("{$MOVIE_ID_ARGUMENT}", movieId)
        }

        fun getArguments(defaultId: String = String.empty()) = listOf(
            navArgument(MOVIE_ID_ARGUMENT) {
                type = NavType.StringType
                defaultValue = defaultId
            }
        )
    }

    companion object{
        const val MOVIE_ID_ARGUMENT = "moviewId"
    }
}