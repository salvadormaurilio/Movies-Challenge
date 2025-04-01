package com.example.kueskichagenge.domain.model

const val DEFAULT_PAGE = 1

data class Movies(
    val page: Int,
    val totalPages: Int,
    var movies: List<Movie>
){
    fun isLastPage() = page >= totalPages
}

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val releaseDate: String,
)