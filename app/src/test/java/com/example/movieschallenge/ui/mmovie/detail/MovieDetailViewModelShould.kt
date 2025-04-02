package com.example.movieschallenge.ui.mmovie.detail

import com.example.movieschallenge.core.TestDispatcherRule
import com.example.movieschallenge.core.assertThatEquals
import com.example.movieschallenge.core.assertThatIsInstanceOf
import com.example.movieschallenge.data.datasource.exception.DataException
import com.example.movieschallenge.domain.GetMovieDetailUseCase
import com.example.movieschallenge.fakedata.ANY_MOVIE_DETAIL_ID
import com.example.movieschallenge.fakedata.givenMovieDetailFakeData
import com.example.movieschallenge.ui.movie.detail.MovieDetailViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class MovieDetailViewModelShould {

    @get:Rule
    var testDispatcherRule = TestDispatcherRule()

    private val getMovieDetailUseCase = mock<GetMovieDetailUseCase>()
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Before
    fun setup() {
        movieDetailViewModel = MovieDetailViewModel(getMovieDetailUseCase, testDispatcherRule.coroutinesDispatchers)
    }

    @Test
    fun `Get MovieDetail data when fetchMovies is success`() = runTest {
        val movieDetail = givenMovieDetailFakeData()
        whenever(getMovieDetailUseCase.fetchMovieDetail(ANY_MOVIE_DETAIL_ID)).thenReturn(flowOf(Result.success(movieDetail)))

        movieDetailViewModel.getMovieDetail(ANY_MOVIE_DETAIL_ID)

        val result = movieDetailViewModel.movieDetailUiState.firstOrNull()

        verify(getMovieDetailUseCase).fetchMovieDetail(ANY_MOVIE_DETAIL_ID)
        assertThatEquals(result?.movieDetail, movieDetail)
    }

    @Test
    fun `Get MovieDetailException data when fetchMovies is failure`() = runTest {
        whenever(getMovieDetailUseCase.fetchMovieDetail(ANY_MOVIE_DETAIL_ID))
            .thenReturn(flowOf(Result.failure(DataException.MovieDetailException())))

        movieDetailViewModel.getMovieDetail(ANY_MOVIE_DETAIL_ID)

        val result = movieDetailViewModel.movieDetailUiState.firstOrNull()

        verify(getMovieDetailUseCase).fetchMovieDetail(ANY_MOVIE_DETAIL_ID)
        assertThatIsInstanceOf<DataException.MovieDetailException>(result?.error)
    }
}
