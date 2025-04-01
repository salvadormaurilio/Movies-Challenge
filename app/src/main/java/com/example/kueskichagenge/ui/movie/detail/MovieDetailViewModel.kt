package com.example.kueskichagenge.ui.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kueskichagenge.core.coroutines.CoroutinesDispatchers
import com.example.kueskichagenge.domain.GetMovieDetailUseCase
import com.example.kueskichagenge.domain.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers,
) : ViewModel() {

    private val _movieDetailUiState = MutableStateFlow(MovieDetailUiState())

    val movieDetailUiState: StateFlow<MovieDetailUiState>
        get() = _movieDetailUiState

    fun getMovieDetail(movieId: Int) = viewModelScope.launch(coroutinesDispatchers.io) {
        emitMovieDetailUiState(isLoading = true)

        getMovieDetailUseCase.fetchMovieDetail(movieId).collect {
            getMovieDetailSuccess(it)
            getMovieDetailError(it)
        }
    }

    private fun getMovieDetailSuccess(result: Result<MovieDetail>) = result.onSuccess {
        emitMovieDetailUiState(movieDetail = it)
    }

    private fun getMovieDetailError(result: Result<MovieDetail>) = result.onFailure {
        it.printStackTrace()
        emitMovieDetailUiState(error = it)
    }

    private fun emitMovieDetailUiState(
        isLoading: Boolean = false,
        movieDetail: MovieDetail? = null,
        error: Throwable? = null
    ) {
        _movieDetailUiState.value = MovieDetailUiState(isLoading = isLoading, movieDetail = movieDetail, error = error)
    }
}
