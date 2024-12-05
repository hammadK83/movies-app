package com.sampleapp.movies.presentation.state

import com.sampleapp.movies.domain.model.Movie

sealed class MoviesListState {
    data object Loading : MoviesListState()
    data class Loaded(val movies: List<Movie>) : MoviesListState()
    data object Failed : MoviesListState()
}