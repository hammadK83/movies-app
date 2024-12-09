package com.sampleapp.movies.data.model

import com.squareup.moshi.Json

data class ConfigurationApiResponseModel (
    @Json(name = "images")
    val imagesApiModel: ImagesApiModel
)