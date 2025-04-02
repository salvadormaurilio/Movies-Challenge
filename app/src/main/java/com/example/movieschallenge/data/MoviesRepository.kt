package com.example.movieschallenge.data

import com.example.movieschallenge.data.datasource.model.toMovieDetail
import com.example.movieschallenge.data.datasource.model.toMovies
import com.example.movieschallenge.data.datasource.remote.MoviesRemoteDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource) {

    fun fetchMovies(query: String, page: Int) = moviesRemoteDataSource.fetchMovies(query, page).map { it.toMovies() }

    fun fetchMovieDetail(movieId: Int) = moviesRemoteDataSource.fetchMovieDetail(movieId).map { it.toMovieDetail() }
}