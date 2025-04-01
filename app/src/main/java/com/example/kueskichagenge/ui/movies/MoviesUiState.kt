package com.example.kueskichagenge.ui.movies

import com.example.kueskichagenge.domain.model.Movies

data class MoviesUiState(
    val isLoading: Boolean = false,
    val movies: Movies? = null,
    val error: Throwable? = null
)
