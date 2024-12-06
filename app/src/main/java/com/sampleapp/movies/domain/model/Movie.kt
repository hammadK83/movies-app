package com.sampleapp.movies.domain.model

data class Movie(
    val id: Long?,
    val posterPath: String?,
    val genreIds: List<Int>?,
    val title: String?,
    val overview: String?,
    val releaseDate: String?
)