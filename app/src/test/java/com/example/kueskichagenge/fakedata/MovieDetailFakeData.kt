package com.example.kueskichagenge.fakedata

import com.example.kueskichagenge.data.datasource.model.MovieDetailResponse
import com.example.kueskichagenge.data.datasource.model.MovieGenreResponse
import com.example.kueskichagenge.data.datasource.model.MovieProductionCompanyResponse
import com.example.kueskichagenge.data.datasource.model.MovieProductionCountryResponse
import com.example.kueskichagenge.data.datasource.model.MovieSpokenLanguageResponse
import com.example.kueskichagenge.domain.model.MovieDetail

const val ANY_MOVIE_DETAIL_ENDPOINT = "/3/movie/1125899?language=en-US"
const val ANY_MOVIE_DETAIL_ID = 1125899

fun givenMovieDetailResponseFakeData() = MovieDetailResponse(
    adult = false,
    backdropPath = "/mwzDApMZAGeYCEVjhegKvCzDX0W.jpg",
    belongsToCollection = null,
    budget = 0,
    genres = listOf(
        MovieGenreResponse(id = 28, name = "Action"),
        MovieGenreResponse(id = 53, name = "Thriller")
    ),
    homepage = "",
    id = 1125899,
    imdbId = "tt27812086",
    originCountry = listOf("GB"),
    originalLanguage = "en",
    originalTitle = "Cleaner",
    overview = "When a group of radical activists take over an energy company's annual gala, seizing 300 hostages, an ex-soldier turned window cleaner suspended 50 storeys up on the outside of the building must save those trapped inside, including her younger brother.",
    popularity = 559.235,
    posterPath = "/mwzDApMZAGeYCEVjhegKvCzDX0W.jpg",
    productionCompanies = listOf(
        MovieProductionCompanyResponse(
            id = 138743,
            logoPath = "/abFJGmtPFs6Bq5cBavmrnXDcpf8.png",
            name = "Anton",
            originCountry = "GB"
        )
    ),
    productionCountries = listOf(
        MovieProductionCountryResponse(
            iso31661 = "GB",
            name = "United Kingdom"
        )
    ),
    releaseDate = "2025-02-19",
    revenue = 0,
    runtime = 96,
    spokenLanguages = listOf(
        MovieSpokenLanguageResponse(
            englishName = "English",
            iso6391 = "en",
            name = "English"
        )
    ),
    status = "Released",
    tagline = "The stakes are a thousand feet high.",
    title = "Cleaner",
    video = false,
    voteAverage = 6.6,
    voteCount = 110
)

fun givenMovieDetailFakeData() = MovieDetail(
    id = 1125899,
    image = "https://image.tmdb.org/t/p/w400/mwzDApMZAGeYCEVjhegKvCzDX0W.jpg",
    title = "Cleaner",
    duration = 96,
    releaseDate = "2025-02-19",
    rating = "6.6",
    genres = listOf("Action", "Thriller"),
    overview = "When a group of radical activists take over an energy company's annual gala, seizing 300 hostages, an ex-soldier turned window cleaner suspended 50 storeys up on the outside of the building must save those trapped inside, including her younger brother."
)