package com.example.kueskichagenge.data

import com.example.kueskichagenge.data.datasource.model.toMovieDetail
import com.example.kueskichagenge.data.datasource.model.toMovies
import com.example.kueskichagenge.data.datasource.remote.MoviesRemoteDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource) {

    fun fetchMovies(query: String, page: Int) = moviesRemoteDataSource.fetchMovies(query, page).map { it.toMovies() }

    fun fetchMovieDetail(movieId: Int) = moviesRemoteDataSource.fetchMovieDetail(movieId).map { it.toMovieDetail() }
}