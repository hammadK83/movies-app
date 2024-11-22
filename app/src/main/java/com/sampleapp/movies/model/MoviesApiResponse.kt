package com.sampleapp.movies.model

import com.squareup.moshi.Json

data class MoviesApiResponse(
    @Json(name = "results")
    val movies: List<Movie>?
)
