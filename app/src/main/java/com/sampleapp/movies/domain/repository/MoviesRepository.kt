package com.sampleapp.movies.domain.repository

import com.sampleapp.movies.domain.model.Movie

interface MoviesRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getFavoriteMovies(): List<Movie>
    suspend fun addFavoriteMovie(movie: Movie)
    suspend fun removeFavoriteMovie(movie: Movie)
}