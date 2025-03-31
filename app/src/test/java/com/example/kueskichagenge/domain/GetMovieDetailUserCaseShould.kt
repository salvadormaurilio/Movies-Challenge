package com.example.kueskichagenge.domain

import com.example.kueskichagenge.core.assertThatEquals
import com.example.kueskichagenge.core.assertThatIsInstanceOf
import com.example.kueskichagenge.data.MoviesRepository
import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.domain.model.MovieDetail
import com.example.kueskichagenge.domain.model.Movies
import com.example.kueskichagenge.fakedata.ANY_MOVIE_DETAIL_ID
import com.example.kueskichagenge.fakedata.givenMovieDetail
import com.example.kueskichagenge.fakedata.givenMoviesFakeData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetMovieDetailUserCaseShould {

    private val moviesRepository = mock<MoviesRepository>()
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    @Before
    fun setup() {
        getMovieDetailUseCase = GetMovieDetailUseCase(moviesRepository)
    }

    @Test
    fun `Get MovieDetail data when fetchMovies is success`() = runTest {
        val movieDetail = givenMovieDetail()
        val resultSuccess = Result.success(movieDetail)
        whenever(moviesRepository.fetchMovieDetail(ANY_MOVIE_DETAIL_ID)).thenReturn(flowOf(resultSuccess))

        val result = getMovieDetailUseCase.fetchMovieDetail(ANY_MOVIE_DETAIL_ID).lastOrNull()

        verify(moviesRepository).fetchMovieDetail(ANY_MOVIE_DETAIL_ID)
        assertThatEquals(result?.getOrNull(), movieDetail)
    }

    @Test
    fun `Get MoviesException data when fetchMovies is failure`() = runTest {
        val resultFailure: Result<MovieDetail> = Result.failure(DataException.MovieDetailException())
        whenever(moviesRepository.fetchMovieDetail(ANY_MOVIE_DETAIL_ID)).thenReturn(flowOf(resultFailure))

        val result = getMovieDetailUseCase.fetchMovieDetail(ANY_MOVIE_DETAIL_ID).lastOrNull()

        verify(moviesRepository).fetchMovieDetail(ANY_MOVIE_DETAIL_ID)
        assertThatIsInstanceOf<DataException.MovieDetailException>(result?.exceptionOrNull())
    }
}