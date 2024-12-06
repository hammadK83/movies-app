package com.sampleapp.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    // DAO provides methods to interact with the database
    abstract fun movieDao(): MovieDao
}