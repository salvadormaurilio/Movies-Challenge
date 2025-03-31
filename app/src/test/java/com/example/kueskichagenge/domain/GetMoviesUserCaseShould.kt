package com.example.kueskichagenge.domain

import com.example.kueskichagenge.core.assertThatEquals
import com.example.kueskichagenge.core.assertThatIsInstanceOf
import com.example.kueskichagenge.data.MoviesRepository
import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.domain.model.Movies
import com.example.kueskichagenge.fakedata.givenMoviesFakeData
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

        whenever(moviesRepository.fetchMovies()).thenReturn(flowOf(resultSuccess))

        val result = getMoviesUseCase.fetchMovies().lastOrNull()
        verify(moviesRepository).fetchMovies()
        assertThatEquals(result?.getOrNull(), movies)
    }

    @Test
    fun `Get MoviesException data when fetchMovies is failure`() = runTest {
        val resultFailure: Result<Movies> = Result.failure(DataException.MoviesException())

        whenever(moviesRepository.fetchMovies()).thenReturn(flowOf(resultFailure))

        val result = getMoviesUseCase.fetchMovies().lastOrNull()
        verify(moviesRepository).fetchMovies()
        assertThatIsInstanceOf<DataException.MoviesException>(result?.exceptionOrNull())
    }
}