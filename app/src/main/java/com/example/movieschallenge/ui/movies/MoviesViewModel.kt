package com.example.movieschallenge.ui.movies

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieschallenge.core.coroutines.CoroutinesDispatchers
import com.example.movieschallenge.core.extensions.empty
import com.example.movieschallenge.core.extensions.orDefault
import com.example.movieschallenge.domain.GetMoviesUseCase
import com.example.movieschallenge.domain.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    val listState = LazyListState()
    var isActiveSearch by mutableStateOf(false)
        private set
    var query by mutableStateOf(String.empty())
        private set
    var jobGetMovies: Job? = null

    private val _moviesUiState = MutableStateFlow(MoviesUiState())
    val moviesUiState = _moviesUiState.asStateFlow()

    private val _navigateToMovieDetail = Channel<Int>()
    val navigateToMovieDetail = _navigateToMovieDetail.receiveAsFlow()

    fun activeSearch(isActiveSearch: Boolean) {
        this.isActiveSearch = isActiveSearch
    }

    fun initGetMovies() {
        if (moviesUiState.value.movies != null) return
        getMovies()
    }

    fun searchMovies(query: String) {
        if (this.query == query) return
        this.query = query
        getMovies()
    }

    fun retryGetMovies() {
        if (moviesUiState.value.isLoading) return
        jobGetMovies?.cancel()
        jobGetMovies = getMovies()
    }

    private fun getMovies() = viewModelScope.launch(coroutinesDispatchers.io) {
        emitMoviesUiState(isLoading = true)
        delay(DELAY_QUERY)
        getMoviesUseCase.fetchMovies(query.trim()).collect {
            getMoviesSuccess(it)
            getMoviesError(it)
        }
    }

    private  fun getMoviesSuccess(result: Result<Movies>) = result.onSuccess {
        viewModelScope.launch { listState.scrollToItem(0) }
        emitMoviesUiState(movies = it)
    }

    private fun getMoviesError(result: Result<Movies>) = result.onFailure {
        emitMoviesUiState(movies = null, error = it)
        it.printStackTrace()
    }

    fun loadMoreMovies() {
        if (moviesUiState.value.movies?.isLastPage() == true || moviesUiState.value.isLoadMore) return

        viewModelScope.launch(coroutinesDispatchers.io) {
            emitMoviesUiState(isLoadMore = true)

            val page = moviesUiState.value.movies?.page.orDefault() + 1
            getMoviesUseCase.fetchMovies(page = page).collect {
                loadMoreMoviesSuccess(it)
                loadMoREMoviesError(it)
            }
        }
    }

    private fun loadMoreMoviesSuccess(result: Result<Movies>) = result.onSuccess {
        val movies = it.copy(movies = moviesUiState.value.movies?.movies.orEmpty() + it.movies)
        emitMoviesUiState(movies = movies)
    }

    private fun loadMoREMoviesError(result: Result<Movies>) = result.onFailure {
        emitMoviesUiState(movies = null, error = it)
        it.printStackTrace()
    }

    private fun emitMoviesUiState(
        isLoading: Boolean = false,
        isLoadMore: Boolean = false,
        movies: Movies? = moviesUiState.value.movies,
        error: Throwable? = null
    ) {
        _moviesUiState.update { current ->
            current.copy(
                isLoading = isLoading,
                isLoadMore = isLoadMore,
                movies = movies,
                error = error
            )
        }
    }

    fun openMovieDetail(movieId: Int) = viewModelScope.launch(coroutinesDispatchers.main) {
        _navigateToMovieDetail.send(movieId)
    }

    companion object {
        const val DELAY_QUERY = 200L
    }
}
