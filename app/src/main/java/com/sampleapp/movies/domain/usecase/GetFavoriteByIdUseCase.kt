package com.sampleapp.movies.domain.usecase

import com.sampleapp.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetFavoriteByIdUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(id: Long) = moviesRepository.getFavoriteById(id)
}