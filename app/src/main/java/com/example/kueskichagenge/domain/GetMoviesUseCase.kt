package com.example.kueskichagenge.domain

import com.example.kueskichagenge.core.extensions.empty
import com.example.kueskichagenge.data.MoviesRepository
import com.example.kueskichagenge.domain.model.DEFAULT_PAGE
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    fun fetchMovies(query: String = String.empty(), page: Int = DEFAULT_PAGE) = moviesRepository.fetchMovies(query, page)
}
