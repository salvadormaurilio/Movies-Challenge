package com.example.kueskichagenge.data.datasource.remote.retrofit

import com.example.kueskichagenge.data.datasource.model.MovieDetailResponse
import com.example.kueskichagenge.data.datasource.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesServiceRetrofit {

    @GET(MOVIES_ENDPOINT)
    suspend fun fetchMovies(): MoviesResponse

    @GET(MOVIE_DETAIL_ENDPOINT)
    suspend fun fetchMovieDetail(@Path(MOVIE_ID_PATH) movieId: Int): MovieDetailResponse
}
