package com.sampleapp.movies.domain.model

import com.sampleapp.movies.domain.usecase.GetFavoriteByIdUseCase

data class Movie(
    val id: Long,
    val posterPath: String?,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val title: String?,
    val overview: String?,
    val releaseDate: String?,
    var isFavorite: Boolean = false
) {
    suspend fun setIsFavorite(getFavoriteByIdUseCase: GetFavoriteByIdUseCase) {
        isFavorite = getFavoriteByIdUseCase.invoke(id) != null
    }
}