package com.example.movieschallenge.ui.home.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.movieschallenge.core.extensions.DEFAULT_VALUE

sealed class HomeRoute(val route: String) {
    data object Movies : HomeRoute("movies")
    data object MovieDetail : HomeRoute("movieDetail/{$MOVIE_ID_ARGUMENT}"){

        fun createRoute(movieId: Int): String {
            return route.replace("{$MOVIE_ID_ARGUMENT}", movieId.toString())
        }

        fun getArguments(defaultId: Int = DEFAULT_VALUE) = listOf(
            navArgument(MOVIE_ID_ARGUMENT) {
                type = NavType.IntType
                defaultValue = defaultId
            }
        )
    }

    companion object{
        const val MOVIE_ID_ARGUMENT = "moviewId"
    }
}
