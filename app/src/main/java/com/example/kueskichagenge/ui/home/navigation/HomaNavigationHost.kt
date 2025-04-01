package com.example.kueskichagenge.ui.home.navigation

import MoviesScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kueskichagenge.core.extensions.orDefault
import com.example.kueskichagenge.ui.home.navigation.HomeRoute.Companion.MOVIE_ID_ARGUMENT
import com.example.kueskichagenge.ui.movie.detail.MovieDetailScreen

@Composable
fun JarsNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = HomeRoute.Movies.route
    ) {
        moviesNav(navController = navHostController)
        movieDetailNav(navController = navHostController)
    }
}

private fun NavGraphBuilder.moviesNav(navController: NavHostController) {
    composable(route = HomeRoute.Movies.route) {
        MoviesScreen(
            openMovieDetail = {
                navController.navigate(HomeRoute.MovieDetail.createRoute(it))
            }
        )
    }
}

private fun NavGraphBuilder.movieDetailNav(navController: NavHostController) {
    composable(
        route = HomeRoute.MovieDetail.route,
        arguments = HomeRoute.MovieDetail.getArguments()
    ) { navBackStackEntry ->
        val movieId = navBackStackEntry.arguments?.getInt(MOVIE_ID_ARGUMENT).orDefault()

        MovieDetailScreen(
            id = movieId,
            onBackClick = { navController.popBackStack() }
        )
    }
}
