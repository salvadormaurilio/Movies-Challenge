package com.example.kueskichagenge.domain.model

data class Movies(
    val page: Int,
    val totalPages: Int,
    val movies: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val releaseDate: String,
)