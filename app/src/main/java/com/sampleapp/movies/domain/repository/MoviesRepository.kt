package com.sampleapp.movies.domain.repository

import com.sampleapp.movies.domain.model.Movie

interface MoviesRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
}