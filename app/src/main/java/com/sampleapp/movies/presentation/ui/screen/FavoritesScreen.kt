package com.sampleapp.movies.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.sampleapp.movies.R
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.presentation.state.MoviesListState
import com.sampleapp.movies.presentation.ui.shared.MoviesList
import com.sampleapp.movies.presentation.ui.shared.ProgressIndicator
import com.sampleapp.movies.presentation.viewmodel.MoviesViewModel

@Composable
fun FavoritesScreen(viewModel: MoviesViewModel, onClickMovie: (movieId: Long) -> Unit) {
    val state = viewModel.favoriteMoviesState.collectAsState().value

    viewModel.getAllFavoriteMovies()

    when (state) {
        is MoviesListState.Loading -> {
            ProgressIndicator()
        }

        is MoviesListState.Loaded -> {
            MoviesList(
                state.movies,
                viewModel,
                onClickFavorite = { movie ->
                    handleOnClickFavorite(viewModel, movie)
                },
                onClickMovie = onClickMovie
            )
        }

        is MoviesListState.Failed -> {
            EmptyText()
        }
    }
}

@Composable
fun EmptyText() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.favorites_screen_empty_list_text),
            fontSize = 18.sp
        )
    }
}

private fun handleOnClickFavorite(viewModel: MoviesViewModel, movie: Movie) {
    viewModel.toggleFavorite(movie)
}