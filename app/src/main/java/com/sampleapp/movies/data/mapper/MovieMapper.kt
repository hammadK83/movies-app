package com.sampleapp.movies.data.mapper

import com.sampleapp.movies.data.model.MovieApiModel
import com.sampleapp.movies.domain.model.Movie

fun MovieApiModel.toDomainModel() =
    Movie(
        id = this.id,
        posterPath = this.posterPath,
        genreIds = this.genreIds,
        title = this.title,
        overview = this.overview,
        releaseDate = this.releaseDate
    )