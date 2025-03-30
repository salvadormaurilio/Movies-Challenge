package com.example.kueskichagenge.domain

import com.example.kueskichagenge.data.datasource.remote.MoviesRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    fun fetchMovieDetail(movieId: Int) = moviesRepository.fetchMovieDetail(movieId)
}
