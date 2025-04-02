package com.example.movieschallenge.ui.movies

import com.example.movieschallenge.domain.model.Movie
import com.example.movieschallenge.domain.model.Movies

fun givenMovies() = Movies(
    page = 1,
    totalPages = 49467,
    movies = movies()
)

private fun movies() = buildList {
    for (i in 1..20 step 2) {
        add(givenMovie1(i))
        add(givenMovie2(i + 1))
    }
}

fun givenMovie1(id: Int = 1125899) = Movie(
    id = id,
    title = "Cleaner",
    overview = "When a group of radical activists take over an energy company's annual gala, seizing 300 hostages, an ex-soldier turned window cleaner suspended 50 storeys up on the outside of the building must save those trapped inside, including her younger brother.",
    poster = "https://image.tmdb.org/t/p/w200/mwzDApMZAGeYCEVjhegKvCzDX0W.jpg",
    releaseDate = "2025-02-19"
)

fun givenMovie2(id: Int = 1165067) = Movie(
    id = id,
    title = "Cosmic Chaos",
    overview = "Battles in virtual reality, survival in a post-apocalyptic wasteland, a Soviet spaceship giving a distress signal - Fantastic stories created with advanced special effects and passion.",
    poster = "https://image.tmdb.org/t/p/w200/mClzWv7gBqgXfjZXp49Enyoex1v.jpg",
    releaseDate = "2023-08-03"
)

