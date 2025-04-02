package com.example.kueskichagenge.data.datasource.remote

import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.data.datasource.remote.retrofit.MoviesServiceRetrofit
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(private val moviesServiceRetrofit: MoviesServiceRetrofit) {

    fun fetchMovies(query: String, page: Int) = flow {
        try {
            val moviesResponse = fetMovies(query, page)
            emit(Result.success(moviesResponse))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Result.failure(DataException.MoviesException()))
        }
    }

    private suspend fun fetMovies(query: String, page: Int) =
        if (query.isBlank()) moviesServiceRetrofit.fetchMovies(page) else moviesServiceRetrofit.searchMovies(query, page)

    fun fetchMovieDetail(movieId: Int) = flow {
        try {
            val moviesResponse = moviesServiceRetrofit.fetchMovieDetail(movieId)

            emit(Result.success(moviesResponse))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Result.failure(DataException.MovieDetailException()))
        }
    }
}
