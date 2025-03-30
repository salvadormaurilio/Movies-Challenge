package com.example.kueskichagenge.data.datasource.remote.retrofit


const val MOVIE_ID_PATH = "movie_id"

const val MOVIES_ENDPOINT = "3/discover/movie?include_adult=false&include_video=false&language=en-US&sort_by=popularity.desc"
const val MOVIE_DETAIL_ENDPOINT = "3/movie/{$MOVIE_ID_PATH}?language=en-US"

