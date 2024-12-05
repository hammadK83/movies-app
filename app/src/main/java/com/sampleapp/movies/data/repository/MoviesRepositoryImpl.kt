package com.sampleapp.movies.data.repository

import com.sampleapp.movies.data.mapper.toDomainModel
import com.sampleapp.movies.data.remote.MoviesApiService
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService
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
}