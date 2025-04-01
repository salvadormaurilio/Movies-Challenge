package com.example.kueskichagenge.domain

import com.example.kueskichagenge.data.MoviesRepository
import com.example.kueskichagenge.domain.model.DEFAULT_PAGE
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    fun fetchMovies(page: Int = DEFAULT_PAGE) = moviesRepository.fetchMovies(page)
}
