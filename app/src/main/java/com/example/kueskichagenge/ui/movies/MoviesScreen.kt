import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.kueskichagenge.R
import com.example.kueskichagenge.core.extensions.empty
import com.example.kueskichagenge.data.datasource.exception.DataException
import com.example.kueskichagenge.domain.model.Movie
import com.example.kueskichagenge.domain.model.Movies
import com.example.kueskichagenge.ui.movies.MoviesViewModel
import com.example.kueskichagenge.ui.movies.givenMovie1
import com.example.kueskichagenge.ui.movies.givenMovies
import com.example.kueskichagenge.ui.theme.KueskiChagengeTheme
import com.example.kueskichagenge.ui.views.CircularProgressIndicatorFixMax
import com.example.kueskichagenge.ui.views.MoviesErrorScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MoviesScreen(viewModel: MoviesViewModel = hiltViewModel(), openMovieDetail: (id: Int) -> Unit) {
    val uiState = viewModel.moviesUiState.collectAsStateWithLifecycle()
    val navigateToMovieDetailChanel = viewModel.navigateToMovieDetail

    LaunchedEffect(Unit) {
        viewModel.getMovies()
    }

    LaunchedEffect(navigateToMovieDetailChanel) {
        viewModel.navigateToMovieDetail.collectLatest {
            openMovieDetail(it)
        }
    }

    MoviesContent(
        isLoading = uiState.value.isLoading,
        movies = uiState.value.movies,
        error = uiState.value.error,
        onMovieClick = { viewModel.openMovieDetail(it) },
        onRetry = { viewModel.getMovies() }
    )
}

@Composable
private fun MoviesContent(
    isLoading: Boolean = false,
    movies: Movies? = null,
    error: Throwable? = null,
    onMovieClick: (id: Int) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Scaffold(
        topBar = { MoviesTopAppBar() },
        content = { paddingValues ->
            Movies(
                modifier = Modifier.padding(paddingValues),
                movies = movies?.movies,
                onMovieClick = onMovieClick
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
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesTopAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                text = stringResource(id = R.string.movies),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}

@Composable
fun Movies(
    modifier: Modifier = Modifier,
    movies: List<Movie>?,
    onMovieClick: (id: Int) -> Unit = {}
) {
    if (movies == null) return
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(
            items = movies,
            key = { it.id }
        ) {
            MovieItem(
                movie = it,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: (id: Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = { onMovieClick(movie.id) }
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = String.empty(),
                model = movie.poster,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    KueskiChagengeTheme {
        MovieItem(
            movie = givenMovie1()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesContentUiStateLoadingPreview() {
    KueskiChagengeTheme {
        MoviesContent(
            isLoading = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesContentUiStateSuccessPreview() {
    KueskiChagengeTheme {
        MoviesContent(
            movies = givenMovies()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesContentUiStateErrorPreview() {
    KueskiChagengeTheme {
        KueskiChagengeTheme {
            MoviesContent(
                error = DataException.MoviesException()
            )
        }
    }
}
