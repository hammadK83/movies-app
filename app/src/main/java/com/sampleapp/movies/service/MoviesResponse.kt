package com.sampleapp.movies.service

import com.sampleapp.movies.model.Movie

sealed class MoviesResponse {
    data class Success(val movies: List<Movie>): MoviesResponse()
    data object Failure: MoviesResponse()
}