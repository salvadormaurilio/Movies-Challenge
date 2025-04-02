package com.example.movieschallenge.domain

import com.example.movieschallenge.data.MoviesRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    fun fetchMovieDetail(movieId: Int) = moviesRepository.fetchMovieDetail(movieId)
}
