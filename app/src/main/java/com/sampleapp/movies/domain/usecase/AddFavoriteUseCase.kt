package com.sampleapp.movies.domain.usecase

import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie) = moviesRepository.addFavoriteMovie(movie)
}