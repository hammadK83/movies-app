package com.sampleapp.movies.data.model

import com.squareup.moshi.Json

data class MoviesApiResponseModel(
    @Json(name = "results")
    val movies: List<MovieApiModel>
)
