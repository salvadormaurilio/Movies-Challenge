package com.example.movieschallenge.ui.movies

import com.example.movieschallenge.domain.model.Movies

data class MoviesUiState(
    val isLoading: Boolean = false,
    val isLoadMore: Boolean = false,
    val movies: Movies? = null,
    val error: Throwable? = null
)
