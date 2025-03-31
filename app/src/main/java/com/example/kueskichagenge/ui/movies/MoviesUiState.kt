package com.example.kueskichagenge.ui.movies

import com.example.kueskichagenge.domain.model.Movie

data class MoviesUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie>? = null,
    val error: Throwable? = null
)
