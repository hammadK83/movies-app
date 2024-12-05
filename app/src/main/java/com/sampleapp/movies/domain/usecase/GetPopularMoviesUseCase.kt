package com.sampleapp.movies.domain.usecase

import com.sampleapp.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke() = moviesRepository.getPopularMovies()
}