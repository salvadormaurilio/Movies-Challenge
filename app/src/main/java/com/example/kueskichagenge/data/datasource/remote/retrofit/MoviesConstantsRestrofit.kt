package com.example.kueskichagenge.data.datasource.remote.retrofit


const val MOVIE_ID = "movie_id"
const val MOVIE_PAGE = "page"
const val MOVIE_QUERY = "query"

const val MOVIES_ENDPOINT = "3/discover/movie?include_adult=false&include_video=false&language=en-US&sort_by=popularity.desc"
const val MOVIES_SEARCH_ENDPOINT = "3/search/movie?&include_adult=false&language=en-US"
const val MOVIE_DETAIL_ENDPOINT = "3/movie/{$MOVIE_ID}?language=en-US"

