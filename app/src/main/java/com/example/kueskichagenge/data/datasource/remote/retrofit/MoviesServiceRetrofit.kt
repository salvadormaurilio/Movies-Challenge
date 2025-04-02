package com.example.kueskichagenge.data.datasource.remote.retrofit

import com.example.kueskichagenge.data.datasource.model.MovieDetailResponse
import com.example.kueskichagenge.data.datasource.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesServiceRetrofit {

    @GET(MOVIES_ENDPOINT)
    suspend fun fetchMovies(@Query(MOVIE_PAGE) page: Int): MoviesResponse


    @GET(MOVIES_SEARCH_ENDPOINT)
    suspend fun searchMovies(@Query(MOVIE_QUERY) query: String,@Query(MOVIE_PAGE) page: Int): MoviesResponse


    @GET(MOVIE_DETAIL_ENDPOINT)
    suspend fun fetchMovieDetail(@Path(MOVIE_ID) movieId: Int): MovieDetailResponse
}
