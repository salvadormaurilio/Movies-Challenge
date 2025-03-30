package com.example.kueskichagenge.domain

import com.example.kueskichagenge.data.datasource.remote.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    fun fetchMovies() = moviesRepository.fetchMovies()
}
