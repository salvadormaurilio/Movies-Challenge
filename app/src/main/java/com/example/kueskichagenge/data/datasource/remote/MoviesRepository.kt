package com.example.kueskichagenge.data.datasource.remote

import com.example.kueskichagenge.data.datasource.model.toMovieDetailResponse
import com.example.kueskichagenge.data.datasource.model.toMovies
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource) {

    fun fetchMovies() = moviesRemoteDataSource.fetchMovies().map { it.toMovies() }

    fun fetchMovieDetail(movieId: Int) = moviesRemoteDataSource.fetchMovieDetail(movieId).map { it.toMovieDetailResponse() }
}
