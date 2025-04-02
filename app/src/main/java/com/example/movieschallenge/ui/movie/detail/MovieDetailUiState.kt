package com.example.movieschallenge.ui.movie.detail

import com.example.movieschallenge.domain.model.MovieDetail

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val error: Throwable? = null
)
