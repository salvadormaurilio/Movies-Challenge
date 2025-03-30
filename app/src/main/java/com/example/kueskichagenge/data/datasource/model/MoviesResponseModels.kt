package com.example.kueskichagenge.data.datasource.model

import com.example.kueskichagenge.core.extensions.orDefault
import com.example.kueskichagenge.domain.model.Movie
import com.example.kueskichagenge.domain.model.Movies
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val dates: MoviesDatesResponse?,
    val page: Int?,
    @SerializedName("results")
    val results: List<MoviesResultResponse?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
)

data class MoviesDatesResponse(
    val maximum: String?,
    val minimum: String?,
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
