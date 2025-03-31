package com.example.kueskichagenge.data

import com.example.kueskichagenge.core.assertThatEquals
import com.example.kueskichagenge.core.assertThatIsInstanceOf
import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.data.datasource.model.MoviesResponse
import com.example.kueskichagenge.data.datasource.remote.MoviesRemoteDataSource
import com.example.kueskichagenge.domain.model.Movies
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
    fun `Get Movies data when fetchMovies is success` () = runTest{
        val moviesResponse = givenMoviesResponseFakeData()
        val movies = givenMoviesFakeData()
        val resultSuccess = Result.success(moviesResponse)

        whenever(moviesRemoteDataSource.fetchMovies()).thenReturn(flowOf(resultSuccess))

        val result = moviesRepository.fetchMovies().lastOrNull()
        verify(moviesRemoteDataSource).fetchMovies()
        assertThatEquals(result?.getOrNull(), movies)
    }

    @Test
    fun `Get MoviesException data when fetchMovies is failure`() = runTest{
        val resultFailure: Result<MoviesResponse> = Result.failure(DataException.MoviesException())

        whenever(moviesRemoteDataSource.fetchMovies()).thenReturn(flowOf(resultFailure))

        val result = moviesRepository.fetchMovies().lastOrNull()
        verify(moviesRemoteDataSource).fetchMovies()
        assertThatIsInstanceOf<DataException.MoviesException>(result?.exceptionOrNull())
    }
}