package com.example.movieschallenge.domain

import com.example.movieschallenge.core.extensions.empty
import com.example.movieschallenge.data.MoviesRepository
import com.example.movieschallenge.domain.model.DEFAULT_PAGE
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    fun fetchMovies(query: String = String.empty(), page: Int = DEFAULT_PAGE) = moviesRepository.fetchMovies(query, page)
}
