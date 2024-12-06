package com.sampleapp.movies.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.presentation.state.MoviesListState
import com.sampleapp.movies.presentation.viewmodel.MoviesViewModel

@Composable
fun MoviesScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    val state = viewModel.moviesListState.collectAsState().value

    if (state == MoviesListState.Loading) {
        viewModel.fetchPopularMovies()
    }

    when (state) {
        is MoviesListState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
        is MoviesListState.Loaded -> {
            MoviesList(state.movies)
        }
        is MoviesListState.Failed -> {
            Text(text = "Failed to load movies", modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun MoviesList(movies: List<Movie>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()) {
        items(movies) { movie ->
            Text(
                text = movie.title.orEmpty(),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}