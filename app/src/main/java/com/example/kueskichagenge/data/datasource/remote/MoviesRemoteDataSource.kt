package com.example.kueskichagenge.data.datasource.remote

import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.data.datasource.remote.retrofit.MoviesServiceRetrofit
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(private val moviesServiceRetrofit: MoviesServiceRetrofit) {

    fun fetchMovies(page: Int) = flow {
        try {
            val moviesResponse = moviesServiceRetrofit.fetchMovies(page.toString())
            emit(Result.success(moviesResponse))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Result.failure(DataException.MoviesException()))
        }
    }

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
