package com.example.kueskichagenge.data.datasource.remote

import com.example.kueskichagenge.core.MockWebServerRule
import com.example.kueskichagenge.core.assertThatEquals
import com.example.kueskichagenge.core.assertThatIsInstanceOf
import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.data.datasource.remote.retrofit.MoviesServiceRetrofit
import com.example.kueskichagenge.fakedata.ANY_MOVIES_ENDPOINT
import com.example.kueskichagenge.fakedata.ANY_MOVIE_DETAIL_ENDPOINT
import com.example.kueskichagenge.fakedata.ANY_MOVIE_DETAIL_ID
import com.example.kueskichagenge.fakedata.givenMovieDetailResponseFakeData
import com.example.kueskichagenge.fakedata.givenMoviesResponseFakeData
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesRemoteDataSourceShould {

    @get:Rule
    val webServerRule = MockWebServerRule()

    private lateinit var moviesRemoteDataSource: MoviesRemoteDataSource

    @Before
    fun setup() {
        moviesRemoteDataSource = MoviesRemoteDataSource(webServerRule.mockRetrofit().create(MoviesServiceRetrofit::class.java))
    }

    @Test
    fun `Get MoviesResponse data when fetchMovies is success`(): Unit = runTest {
        val moviesResponse = givenMoviesResponseFakeData()
        webServerRule.loadMockResponse(fileName = "moviesResponse.json")

        val result = moviesRemoteDataSource.fetchMovies().lastOrNull()

        webServerRule.assertRequestMethod(path = ANY_MOVIES_ENDPOINT, method = MockWebServerRule.GET)
        assertThatEquals(result?.getOrNull(), moviesResponse)
    }

    @Test
    fun `Get MoviesException data when fetchMovies is failure`() = runTest {
        webServerRule.loadMockResponse(responseCode = 400)

        val result = moviesRemoteDataSource.fetchMovies().lastOrNull()

        webServerRule.assertRequestMethod(path = ANY_MOVIES_ENDPOINT, method = MockWebServerRule.GET)
        assertThatIsInstanceOf<DataException.MoviesException>(result?.exceptionOrNull())
    }

    @Test
    fun `Get MovieDetailResponse data when fetchMovieDetail is success`(): Unit = runTest {
        val movieDetailResponse = givenMovieDetailResponseFakeData()
        webServerRule.loadMockResponse(fileName = "movieDetailResponse.json")

        val result = moviesRemoteDataSource.fetchMovieDetail(ANY_MOVIE_DETAIL_ID).lastOrNull()

        webServerRule.assertRequestMethod(path = ANY_MOVIE_DETAIL_ENDPOINT, method = MockWebServerRule.GET)
        assertThatEquals(result?.getOrNull(), movieDetailResponse)
    }

    @Test
    fun `Get MovieDetailException data when fetchMovieDetail is failure`() = runTest {
        webServerRule.loadMockResponse(responseCode = 400)

        val result = moviesRemoteDataSource.fetchMovieDetail(ANY_MOVIE_DETAIL_ID).lastOrNull()

        webServerRule.assertRequestMethod(path = ANY_MOVIE_DETAIL_ENDPOINT, method = MockWebServerRule.GET)
        assertThatIsInstanceOf<DataException.MovieDetailException>(result?.exceptionOrNull())
    }
}