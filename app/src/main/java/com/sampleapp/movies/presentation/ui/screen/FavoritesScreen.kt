package com.sampleapp.movies.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sampleapp.movies.R
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.presentation.state.MoviesListState
import com.sampleapp.movies.presentation.ui.shared.MoviesList
import com.sampleapp.movies.presentation.viewmodel.MoviesViewModel

@Composable
fun FavoritesScreen(viewModel: MoviesViewModel) {
    val state = viewModel.favoriteMoviesState.collectAsState().value

    viewModel.getAllFavoriteMovies()

    when (state) {
        is MoviesListState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        is MoviesListState.Loaded -> {
            MoviesList(state.movies, viewModel) { movie ->
                handleOnClickFavorite(viewModel, movie)
            }
        }

        is MoviesListState.Failed -> {
            Text(
                text = stringResource(R.string.favorites_screen_empty_list_text),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private fun handleOnClickFavorite(viewModel: MoviesViewModel, movie: Movie) {
    viewModel.removeFavorite(movie)
    viewModel.updateFavoritesInMoviesList(movie, false)
}