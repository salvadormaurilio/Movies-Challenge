package com.example.kueskichagenge.data

import com.example.kueskichagenge.core.assertThatEquals
import com.example.kueskichagenge.core.assertThatIsInstanceOf
import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.data.datasource.model.MovieDetailResponse
import com.example.kueskichagenge.data.datasource.model.MoviesResponse
import com.example.kueskichagenge.data.datasource.remote.MoviesRemoteDataSource
import com.example.kueskichagenge.fakedata.ANY_MOVIE_DETAIL_ID
import com.example.kueskichagenge.fakedata.ANY_PAGE
import com.example.kueskichagenge.fakedata.ANY_QUERY
import com.example.kueskichagenge.fakedata.givenMovieDetailFakeData
import com.example.kueskichagenge.fakedata.givenMovieDetailResponseFakeData
import com.example.kueskichagenge.fakedata.givenMoviesFakeData
import com.example.kueskichagenge.fakedata.givenMoviesResponseFakeData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MoviesRepositoryShould {

    private val moviesRemoteDataSource = mock<MoviesRemoteDataSource>()
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setup() {
        moviesRepository = MoviesRepository(moviesRemoteDataSource)
    }

    @Test
    fun `Get Movies data when fetchMovies is success`() = runTest {
        val moviesResponse = givenMoviesResponseFakeData()
        val movies = givenMoviesFakeData()
        val resultSuccess = Result.success(moviesResponse)
        whenever(moviesRemoteDataSource.fetchMovies(ANY_QUERY,ANY_PAGE)).thenReturn(flowOf(resultSuccess))

        val result = moviesRepository.fetchMovies(ANY_QUERY, ANY_PAGE).lastOrNull()

        verify(moviesRemoteDataSource).fetchMovies(ANY_QUERY, ANY_PAGE)
        assertThatEquals(result?.getOrNull(), movies)
    }

    @Test
    fun `Get MoviesException data when fetchMovies is failure`() = runTest {
        val resultFailure: Result<MoviesResponse> = Result.failure(DataException.MoviesException())
        whenever(moviesRemoteDataSource.fetchMovies(ANY_QUERY, ANY_PAGE)).thenReturn(flowOf(resultFailure))

        val result = moviesRepository.fetchMovies(ANY_QUERY, ANY_PAGE).lastOrNull()

        verify(moviesRemoteDataSource).fetchMovies(ANY_QUERY, ANY_PAGE)
        assertThatIsInstanceOf<DataException.MoviesException>(result?.exceptionOrNull())
    }

    @Test
    fun `Get MovieDetail data when fetchMovieDetail is success`() = runTest {
        val movieDetailResponse = givenMovieDetailResponseFakeData()
        val movieDetail = givenMovieDetailFakeData()
        val resultSuccess = Result.success(movieDetailResponse)
        whenever(moviesRemoteDataSource.fetchMovieDetail(ANY_MOVIE_DETAIL_ID)).thenReturn(flowOf(resultSuccess))

        val result = moviesRepository.fetchMovieDetail(ANY_MOVIE_DETAIL_ID).lastOrNull()

        verify(moviesRemoteDataSource).fetchMovieDetail(ANY_MOVIE_DETAIL_ID)
        assertThatEquals(result?.getOrNull(), movieDetail)
    }

    @Test
    fun `Get MovieDetailException data when fetchMovieDetail is failure`() = runTest {
        val resultFailure: Result<MovieDetailResponse> = Result.failure(DataException.MovieDetailException())
        whenever(moviesRemoteDataSource.fetchMovieDetail(ANY_MOVIE_DETAIL_ID)).thenReturn(flowOf(resultFailure))

        val result = moviesRepository.fetchMovieDetail(ANY_MOVIE_DETAIL_ID).lastOrNull()

        verify(moviesRemoteDataSource).fetchMovieDetail(ANY_MOVIE_DETAIL_ID)
        assertThatIsInstanceOf<DataException.MovieDetailException>(result?.exceptionOrNull())
    }
}