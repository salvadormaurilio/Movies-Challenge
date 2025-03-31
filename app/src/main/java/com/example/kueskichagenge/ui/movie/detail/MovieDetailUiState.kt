package com.example.kueskichagenge.ui.movie.detail

import com.example.kueskichagenge.domain.model.MovieDetail

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val error: Throwable? = null
)
