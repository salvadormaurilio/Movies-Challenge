package com.example.kueskichagenge.data.datasource.model

import com.example.kueskichagenge.core.extensions.orDefault
import com.example.kueskichagenge.domain.model.Movie
import com.example.kueskichagenge.domain.model.Movies
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int?,
    @SerializedName("results")
    val results: List<MoviesResultResponse?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
)

data class MoviesResultResponse(
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
)

fun Result<MoviesResponse>.toMovies() = map { it.toMovies() }

fun MoviesResponse.toMovies() = Movies(
    totalPages = totalPages.orDefault(),
    page = page.orDefault(),
    movies = results.toMovies()
)

fun List<MoviesResultResponse?>?.toMovies() = this?.mapNotNull { it?.toMovie() }.orEmpty()

fun MoviesResultResponse.toMovie() = Movie(
    id = id.orDefault(),
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    poster = BASE_MOVIE_URL + posterPath.orEmpty(),
    releaseDate = releaseDate.orEmpty()
)

private const val BASE_MOVIE_URL = "https://image.tmdb.org/t/p/w200"
