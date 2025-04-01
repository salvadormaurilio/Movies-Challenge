package com.example.kueskichagenge.data

import com.example.kueskichagenge.data.datasource.model.toMovieDetail
import com.example.kueskichagenge.data.datasource.model.toMovies
import com.example.kueskichagenge.data.datasource.remote.MoviesRemoteDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource) {

    fun fetchMovies(page: Int) = moviesRemoteDataSource.fetchMovies(page).map { it.toMovies() }

    fun fetchMovieDetail(movieId: Int) = moviesRemoteDataSource.fetchMovieDetail(movieId).map { it.toMovieDetail() }
}