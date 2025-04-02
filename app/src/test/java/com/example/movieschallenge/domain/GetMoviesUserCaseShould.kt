package com.example.movieschallenge.domain

import com.example.movieschallenge.core.assertThatEquals
import com.example.movieschallenge.core.assertThatIsInstanceOf
import com.example.movieschallenge.data.MoviesRepository
import com.example.movieschallenge.data.datasource.exception.DataException
import com.example.movieschallenge.domain.model.DEFAULT_PAGE
import com.example.movieschallenge.domain.model.Movies
import com.example.movieschallenge.fakedata.ANY_QUERY
import com.example.movieschallenge.fakedata.givenMoviesFakeData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetMoviesUserCaseShould {

    private val moviesRepository = mock<MoviesRepository>()
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Before
    fun setup() {
        getMoviesUseCase = GetMoviesUseCase(moviesRepository)
    }

    @Test
    fun `Get Movies data when fetchMovies is success`() = runTest {
        val movies = givenMoviesFakeData()
        val resultSuccess = Result.success(movies)
        whenever(moviesRepository.fetchMovies(ANY_QUERY, DEFAULT_PAGE)).thenReturn(flowOf(resultSuccess))

        val result = getMoviesUseCase.fetchMovies(ANY_QUERY, DEFAULT_PAGE).lastOrNull()

        verify(moviesRepository).fetchMovies(ANY_QUERY, DEFAULT_PAGE)
        assertThatEquals(result?.getOrNull(), movies)
    }

    @Test
    fun `Get MoviesException data when fetchMovies is failure`() = runTest {
        val resultFailure: Result<Movies> = Result.failure(DataException.MoviesException())
        whenever(moviesRepository.fetchMovies(ANY_QUERY, DEFAULT_PAGE)).thenReturn(flowOf(resultFailure))

        val result = getMoviesUseCase.fetchMovies(ANY_QUERY, DEFAULT_PAGE).lastOrNull()

        verify(moviesRepository).fetchMovies(ANY_QUERY, DEFAULT_PAGE)
        assertThatIsInstanceOf<DataException.MoviesException>(result?.exceptionOrNull())
    }
}