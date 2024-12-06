package com.sampleapp.movies.di

import android.content.Context
import androidx.room.Room
import com.sampleapp.movies.data.local.MovieDao
import com.sampleapp.movies.data.local.MovieDatabase
import com.sampleapp.movies.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            Constants.MOVIES_DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }
}