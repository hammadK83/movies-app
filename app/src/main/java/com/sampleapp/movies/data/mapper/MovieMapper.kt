package com.sampleapp.movies.data.mapper

import com.sampleapp.movies.data.local.MovieEntity
import com.sampleapp.movies.data.model.ConfigurationApiResponseModel
import com.sampleapp.movies.data.model.GenreApiModel
import com.sampleapp.movies.data.model.MovieApiModel
import com.sampleapp.movies.domain.model.Configuration
import com.sampleapp.movies.domain.model.Genre
import com.sampleapp.movies.domain.model.Movie

fun MovieApiModel.toDomainModel() =
    Movie(
        id = this.id,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        title = this.title,
        overview = this.overview,
        releaseDate = this.releaseDate
    )

fun MovieEntity.toDomainModel() =
    Movie(
        id = this.id,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        title = this.title,
        overview = this.overview,
        releaseDate = this.releaseDate,
        isFavorite = true
    )

fun Movie.toDbEntity() =
    MovieEntity(
        id = this.id,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        title = this.title,
        overview = this.overview,
        releaseDate = this.releaseDate
    )

fun ConfigurationApiResponseModel.toDomainModel() =
    Configuration(
        imageBaseUrl = this.imagesApiModel.baseUrl
    )

fun GenreApiModel.toDomainModel() =
    Genre(
        id = this.id,
        name = this.name
    )