package com.sampleapp.movies.model

import com.squareup.moshi.Json

data class Movie(
    val id: Int?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val title: String?,
    val overview: String?,
    @Json(name = "release_date")
    val releaseDate: String?
)