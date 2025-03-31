package com.example.kueskichagenge.fakedata

import com.example.kueskichagenge.data.datasource.model.MoviesResponse
import com.example.kueskichagenge.data.datasource.model.MoviesResultResponse
import com.example.kueskichagenge.domain.model.Movie
import com.example.kueskichagenge.domain.model.Movies

const val ANY_MOVIES_ENDPOINT = "/3/discover/movie?include_adult=false&include_video=false&language=en-US&sort_by=popularity.desc"

fun givenMoviesResponseFakeData() = MoviesResponse(
    page = 1,
    results = listOf(
        MoviesResultResponse(
            adult = false,
            backdropPath = "/gsQJOfeW45KLiQeEIsom94QPQwb.jpg",
            genreIds = listOf(28, 53),
            id = 1125899,
            originalLanguage = "en",
            originalTitle = "Cleaner",
            overview = "When a group of radical activists take over an energy company's annual gala, seizing 300 hostages, an ex-soldier turned window cleaner suspended 50 storeys up on the outside of the building must save those trapped inside, including her younger brother.",
            popularity = 559.235,
            posterPath = "/mwzDApMZAGeYCEVjhegKvCzDX0W.jpg",
            releaseDate = "2025-02-19",
            title = "Cleaner",
            video = false,
            voteAverage = 6.6,
            voteCount = 107
        ),
        MoviesResultResponse(
            adult = false,
            backdropPath = "/m2mzlsJjE3UAqeUB5fLUkpWg4Iq.jpg",
            genreIds = listOf(53, 878),
            id = 1165067,
            originalLanguage = "en",
            originalTitle = "Cosmic Chaos",
            overview = "Battles in virtual reality, survival in a post-apocalyptic wasteland, a Soviet spaceship giving a distress signal - Fantastic stories created with advanced special effects and passion.",
            popularity = 539.5847,
            posterPath = "/mClzWv7gBqgXfjZXp49Enyoex1v.jpg",
            releaseDate = "2023-08-03",
            title = "Cosmic Chaos",
            video = false,
            voteAverage = 5.7,
            voteCount = 13
        )
    ),
    totalPages = 49467,
    totalResults = 989327
)

fun givenMoviesFakeData() = Movies(
    page = 1,
    totalPages = 49467,
    movies = listOf(
        Movie(
            id = 1125899,
            title = "Cleaner",
            overview = "When a group of radical activists take over an energy company's annual gala, seizing 300 hostages, an ex-soldier turned window cleaner suspended 50 storeys up on the outside of the building must save those trapped inside, including her younger brother.",
            poster = "https://image.tmdb.org/t/p/w200/mwzDApMZAGeYCEVjhegKvCzDX0W.jpg",
            releaseDate = "2025-02-19"
        ),
        Movie(
            id = 1165067,
            title = "Cosmic Chaos",
            overview = "Battles in virtual reality, survival in a post-apocalyptic wasteland, a Soviet spaceship giving a distress signal - Fantastic stories created with advanced special effects and passion.",
            poster = "https://image.tmdb.org/t/p/w200/mClzWv7gBqgXfjZXp49Enyoex1v.jpg",
            releaseDate = "2023-08-03"
        )
    )
)
