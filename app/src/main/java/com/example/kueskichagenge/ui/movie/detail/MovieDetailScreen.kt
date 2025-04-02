package com.example.kueskichagenge.ui.movie.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.kueskichagenge.R
import com.example.kueskichagenge.core.extensions.empty
import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.domain.model.MovieDetail
import com.example.kueskichagenge.ui.theme.KueskiChagengeTheme
import com.example.kueskichagenge.ui.views.CircularProgressIndicatorFixMax
import com.example.kueskichagenge.ui.views.GenreChip
import com.example.kueskichagenge.ui.views.MoviesErrorScreen
import com.example.kueskichagenge.ui.views.TextWithIcon

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    id: Int,
    onBackClick: () -> Unit
) {
    val uiState = viewModel.movieDetailUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getMovieDetail(id)
    }

    MovieDetailContent(
        isLoading = uiState.value.isLoading,
        movieDetail = uiState.value.movieDetail,
        error = uiState.value.error,
        onBackClick = onBackClick,
        onRetry = { viewModel.getMovieDetail(id) }
    )
}

@Composable
fun MovieDetailContent(
    isLoading: Boolean = false,
    movieDetail: MovieDetail? = null,
    error: Throwable? = null,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Scaffold(
        topBar = { MovieDetailTopAppBar(onBackClick = onBackClick) },
        content =
            { paddingValues ->
                MovieDetail(
                    modifier = Modifier.padding(paddingValues), movieDetail
                )

                MoviesErrorScreen(
                    modifier = Modifier.padding(paddingValues),
                    error = error,
                    onRetry = onRetry
                )

                CircularProgressIndicatorFixMax(
                    modifier = Modifier.padding(paddingValues),
                    isVisible = isLoading
                )
            })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailTopAppBar(onBackClick: () -> Unit = {}) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                text = stringResource(id = R.string.detail),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = String.empty()
                    )
            }
        }
    )
}

@Composable
private fun MovieDetail(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail?
) {
    if (movieDetail == null) return
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            AsyncImage(
                model = movieDetail.image,
                contentDescription = String.empty(),
                modifier = Modifier
                    .width(150.dp)
                    .height(225.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = movieDetail.title,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movieDetail.releaseDate,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                GenreChip(genres = movieDetail.genres)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextWithIcon(
                        icon = Icons.Default.Star,
                        text = movieDetail.rating
                    )
                    TextWithIcon(
                        icon = Icons.Default.Notifications,
                        text = stringResource(id = R.string.duration, movieDetail.duration)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = movieDetail.overview,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailContentUiStateLoadingPreview() {
    KueskiChagengeTheme {
        MovieDetailContent(
            isLoading = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailContentUiStateSuccessPreview() {
    KueskiChagengeTheme {
        MovieDetailContent(
            movieDetail = givenMovieDetail()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailContentUiStateErrorPreview() {
    KueskiChagengeTheme {
        KueskiChagengeTheme {
            MovieDetailContent(
                error = DataException.MovieDetailException()
            )
        }
    }
}
