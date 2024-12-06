package com.sampleapp.movies.data.repository

import com.sampleapp.movies.data.local.MovieDatabase
import com.sampleapp.movies.data.mapper.toDbEntity
import com.sampleapp.movies.data.mapper.toDomainModel
import com.sampleapp.movies.data.remote.MoviesApiService
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService,
    private val movieDatabase: MovieDatabase
) : MoviesRepository {
    override suspend fun getPopularMovies(): Result<List<Movie>> =
        try {
            // Perform API call
            val apiResponse = moviesApiService.getPopularMovies()
            // Map API response to domain models
            Result.success(apiResponse.movies.map { it.toDomainModel() })
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    override suspend fun getFavoriteMovies(): List<Movie> =
        movieDatabase.movieDao().getAllFavoriteMovies().map {
            it.toDomainModel()
        }

    override suspend fun addFavoriteMovie(movie: Movie) {
        movieDatabase.movieDao().insertMovie(movie.toDbEntity())
    }

    override suspend fun removeFavoriteMovie(movie: Movie) {
        movieDatabase.movieDao().deleteMovie(movie.toDbEntity())
    }
}