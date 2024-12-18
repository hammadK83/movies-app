package com.sampleapp.movies.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.presentation.state.MoviesListState
import com.sampleapp.movies.presentation.ui.shared.MoviesList
import com.sampleapp.movies.presentation.ui.shared.ProgressIndicator
import com.sampleapp.movies.presentation.viewmodel.MoviesViewModel

@Composable
fun MoviesScreen(viewModel: MoviesViewModel, onClickMovie: (movieId: Long) -> Unit) {
    val state = viewModel.moviesListState.collectAsState().value

    if (state == MoviesListState.Loading) {
        viewModel.fetchPopularMovies()
    }

    when (state) {
        is MoviesListState.Loading -> {
            ProgressIndicator()
        }

        is MoviesListState.Loaded -> {
            MoviesList(
                state.movies,
                viewModel,
                onClickFavorite = { movie ->
                    handeOnClickFavorite(viewModel, movie)
                },
                onClickMovie = onClickMovie
            )
        }

        is MoviesListState.Failed -> {
            Text(text = "Failed to load movies", modifier = Modifier.fillMaxSize())
        }
    }
}

private fun handeOnClickFavorite(viewModel: MoviesViewModel, movie: Movie) {
    viewModel.toggleFavorite(movie)
}