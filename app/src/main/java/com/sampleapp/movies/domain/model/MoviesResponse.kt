package com.sampleapp.movies.domain.model

import com.sampleapp.movies.data.model.MovieApiModel

sealed class MoviesResponse {
    data class Success(val movies: List<MovieApiModel>): MoviesResponse()
    data object Failure: MoviesResponse()
}