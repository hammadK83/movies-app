package com.sampleapp.movies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "favorite_movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val posterPath: String?,
    val title: String?,
    val overview: String?,
    val releaseDate: String?,
    @TypeConverters(Converters::class) val genreIds: List<Int>?
)