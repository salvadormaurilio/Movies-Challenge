package com.example.kueskichagenge.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kueskichagenge.core.coroutines.CoroutinesDispatchers
import com.example.kueskichagenge.domain.GetMoviesUseCase
import com.example.kueskichagenge.domain.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val _moviesUiState = MutableStateFlow(MoviesUiState())
    val moviesUiState = _moviesUiState.asStateFlow()

    private val _navigateToMovieDetail = Channel<Int>()
    val navigateToMovieDetail = _navigateToMovieDetail.receiveAsFlow()

    fun getMovies() = viewModelScope.launch(coroutinesDispatchers.io) {
        emitMoviesUiState(isLoading = true)

        getMoviesUseCase.fetchMovies().collect {
            getMoviesSuccess(it)
            getMoviesError(it)
        }
    }

    private fun getMoviesSuccess(result: Result<Movies>) = result.onSuccess {
        emitMoviesUiState(movies = it)
    }

    private fun getMoviesError(result: Result<Movies>) = result.onFailure {
        it.printStackTrace()
        emitMoviesUiState(error = it)
    }

    private fun emitMoviesUiState(
        isLoading: Boolean = false,
        movies: Movies? = null,
        error: Throwable? = null
    ) {
        _moviesUiState.value = MoviesUiState(isLoading = isLoading, movies = movies, error = error)
    }

    fun openMovieDetail(movieId: Int) = viewModelScope.launch(coroutinesDispatchers.main) {
        _navigateToMovieDetail.send(movieId)
    }
}
