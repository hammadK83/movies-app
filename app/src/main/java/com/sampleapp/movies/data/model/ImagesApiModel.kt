package com.sampleapp.movies.data.model

import com.squareup.moshi.Json

data class ImagesApiModel(
    @Json(name = "secure_base_url")
    val baseUrl: String,
)