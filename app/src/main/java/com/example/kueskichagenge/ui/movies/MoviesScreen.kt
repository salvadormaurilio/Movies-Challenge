import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    openMovieDetail: (id: Int) -> Unit
) {
    val uiState = viewModel.moviesUiState.collectAsStateWithLifecycle()
    val navigateToMovieDetailChanel = viewModel.navigateToMovieDetail

    LaunchedEffect(Unit) {
        viewModel.initGetMovies()
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
        isActiveSearch = viewModel.isActiveSearch,
        query = viewModel.query,
        listState = viewModel.listState,
        onActiveSearch = viewModel::activeSearch,
        onSearch = viewModel::searchMovies,
        onRetry = viewModel::retryGetMovies,
        onLoadMore = viewModel::loadMoreMovies,
        onMovieClick = viewModel::openMovieDetail
    )
}

@Composable
private fun MoviesContent(
    isActiveSearch: Boolean = false,
    query: String = String.empty(),
    listState: LazyListState = LazyListState(),
    isLoading: Boolean = false,
    movies: Movies? = null,
    error: Throwable? = null,
    onActiveSearch: (Boolean) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onRetry: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    onMovieClick: (id: Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            MoviesTopAppBar(
                isActiveSearch = isActiveSearch,
                query = query,
                onActiveSearch = onActiveSearch,
                onSearch = onSearch
            )
        },
        content = { paddingValues ->
            Movies(
                modifier = Modifier.padding(paddingValues),
                listState = listState,
                movies = movies,
                onLoadMore = onLoadMore,
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
fun MoviesTopAppBar(
    isActiveSearch: Boolean,
    query: String = String.empty(),
    onActiveSearch: (Boolean) -> Unit = {},
    onSearch: (String) -> Unit = {},
) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            if (!isActiveSearch) {
                Text(
                    text = stringResource(id = R.string.movies),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )

            } else {
                TextField(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxWidth()
                        .focusRequester(FocusRequester()),
                    textStyle = MaterialTheme.typography.titleSmall,
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    value = query,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.search),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            onActiveSearch(false)
                            onSearch(String.empty())
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = String.empty(),
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = { onSearch(String.empty()) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = String.empty()
                            )
                        }
                    },
                    onValueChange = { onSearch(it) },
                )
            }
        },
        actions = {
            if (!isActiveSearch) {
                IconButton(onClick = { onActiveSearch(true) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = String.empty()
                    )
                }
            }
        }
    )
}

@Composable
fun Movies(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    movies: Movies?,
    onLoadMore: () -> Unit = {},
    onMovieClick: (id: Int) -> Unit = {}
) {
    if (movies == null) return

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        state = listState
    ) {
        items(
            items = movies.movies
        ) {
            MovieItem(
                movie = it,
                onMovieClick = onMovieClick
            )
        }
        if (!movies.isLastPage()) {
            item {
                LoadMoreItem(
                    key = movies,
                    onLoadMore = onLoadMore
                )
            }
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

@Composable
private fun LoadMoreItem(
    key: Any? = null,
    onLoadMore: () -> Unit = {}
) {
    LaunchedEffect(key) {
        onLoadMore()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
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
fun LoadMoreItemPreview() {
    KueskiChagengeTheme {
        LoadMoreItem()
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
        var isActiveSearch by rememberSaveable { mutableStateOf(false) }
        var query by rememberSaveable { mutableStateOf(String.empty()) }
        MoviesContent(
            isActiveSearch = isActiveSearch,
            query = query,
            onSearch = { query = it },
            onActiveSearch = { isActiveSearch = it },
            movies = givenMovies(),
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
