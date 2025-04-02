package com.example.movieschallenge.domain.model

data class MovieDetail(
    val id: Int,
    val image: String,
    val title: String,
    val duration: Int,
    val releaseDate: String,
    val rating: String,
    val genres: List<String>,
    val overview: String
)