package com.example.kueskichagenge.ui.movies

import com.example.kueskichagenge.core.TestDispatcherRule
import com.example.kueskichagenge.core.assertThatEquals
import com.example.kueskichagenge.core.assertThatIsInstanceOf
import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.domain.GetMoviesUseCase
import com.example.kueskichagenge.fakedata.ANY_MOVIE_DETAIL_ID
import com.example.kueskichagenge.fakedata.ANY_PAGE
import com.example.kueskichagenge.fakedata.ANY_QUERY
import com.example.kueskichagenge.fakedata.givenMoviesFakeData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class MoviesViewModelShould {

    @get:Rule
    var testDispatcherRule = TestDispatcherRule()

    private val getMoviesUseCase = mock<GetMoviesUseCase>()
    private lateinit var moviesViewModel: MoviesViewModel

    @Before
    fun setup() {
        moviesViewModel = MoviesViewModel(getMoviesUseCase, testDispatcherRule.coroutinesDispatchers)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get Movies data when initGetMovies is called and fetchMovies is success`() = runTest {
        val movies = givenMoviesFakeData()
        whenever(getMoviesUseCase.fetchMovies()).thenReturn(flowOf(Result.success(movies)))

        moviesViewModel.initGetMovies()
        advanceUntilIdle()

        val result = moviesViewModel.moviesUiState.firstOrNull()

        verify(getMoviesUseCase).fetchMovies()
        assertThatEquals(result?.movies, movies)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get MoviesException data when initGetMovies is called and fetchMovies is failure`() = runTest {
        whenever(getMoviesUseCase.fetchMovies()).thenReturn(flowOf(Result.failure(DataException.MoviesException())))

        moviesViewModel.initGetMovies()
        advanceUntilIdle()

        val result = moviesViewModel.moviesUiState.firstOrNull()

        verify(getMoviesUseCase).fetchMovies()
        assertThatIsInstanceOf<DataException.MoviesException>(result?.error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get Movies data when searchMovies is called and fetchMovies is success`() = runTest {
        val movies = givenMoviesFakeData()
        whenever(getMoviesUseCase.fetchMovies(query = ANY_QUERY)).thenReturn(flowOf(Result.success(movies)))

        moviesViewModel.searchMovies(ANY_QUERY)
        advanceUntilIdle()

        val result = moviesViewModel.moviesUiState.firstOrNull()

        verify(getMoviesUseCase).fetchMovies(ANY_QUERY)
        assertThatEquals(result?.movies, movies)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get MoviesException data when searchMovies is called and fetchMovies is failure`() = runTest {
        whenever(getMoviesUseCase.fetchMovies(query = ANY_QUERY)).thenReturn(flowOf(Result.failure(DataException.MoviesException())))

        moviesViewModel.searchMovies(ANY_QUERY)
        advanceUntilIdle()

        val result = moviesViewModel.moviesUiState.firstOrNull()

        verify(getMoviesUseCase).fetchMovies(ANY_QUERY)
        assertThatIsInstanceOf<DataException.MoviesException>(result?.error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get Movies data when retryGetMovies is called and fetchMovies is success`() = runTest {
        val movies = givenMoviesFakeData()
        whenever(getMoviesUseCase.fetchMovies()).thenReturn(flowOf(Result.success(movies)))

        moviesViewModel.retryGetMovies()
        advanceUntilIdle()

        val result = moviesViewModel.moviesUiState.firstOrNull()

        verify(getMoviesUseCase).fetchMovies()
        assertThatEquals(result?.movies, movies)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Get MoviesException data when retryGetMovies is called and fetchMovies is failure`() = runTest {
        whenever(getMoviesUseCase.fetchMovies()).thenReturn(flowOf(Result.failure(DataException.MoviesException())))

        moviesViewModel.retryGetMovies()
        advanceUntilIdle()

        val result = moviesViewModel.moviesUiState.firstOrNull()

        verify(getMoviesUseCase).fetchMovies()
        assertThatIsInstanceOf<DataException.MoviesException>(result?.error)
    }

    @Test
    fun `Get Movies data when loadMoreMovies is success`() = runTest {
        val movies = givenMoviesFakeData()
        whenever(getMoviesUseCase.fetchMovies(page = ANY_PAGE)).thenReturn(flowOf(Result.success(movies)))

        moviesViewModel.loadMoreMovies()

        val result = moviesViewModel.moviesUiState.firstOrNull()

        verify(getMoviesUseCase).fetchMovies(page = ANY_PAGE)
        assertThatEquals(result?.movies, movies)
    }

    @Test
    fun `Get MoviesException data when loadMoreMovies is failure`() = runTest {
        whenever(getMoviesUseCase.fetchMovies(page = ANY_PAGE)).thenReturn(flowOf(Result.failure(DataException.MoviesException())))

        moviesViewModel.loadMoreMovies()

        val result = moviesViewModel.moviesUiState.firstOrNull()

        verify(getMoviesUseCase).fetchMovies(page = ANY_PAGE)
        assertThatIsInstanceOf<DataException.MoviesException>(result?.error)
    }

    @Test
    fun `Navigate to movieDetail when openMovieDetail is called`() = runTest {
        moviesViewModel.openMovieDetail(ANY_MOVIE_DETAIL_ID)

        val result = moviesViewModel.navigateToMovieDetail.firstOrNull()

        assertThatEquals(result, ANY_MOVIE_DETAIL_ID)
    }
}
