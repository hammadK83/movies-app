package com.sampleapp.movies.service

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"

class MoviesApi {
    private val moviesApiService: MoviesApiService by lazy {
        creteRetrofitService()
    }

    suspend fun getPopularMovies(): MoviesResponse {
        val result = runCatching {
            moviesApiService.getPopularMovies()
        }

        if (result.isSuccess) {
            val movies = result.getOrNull()?.movies ?: listOf()
            return MoviesResponse.Success(movies)
        } else {
            Log.d("MoviesApi", "Error getting popular movies ${result.exceptionOrNull()}")
            return MoviesResponse.Failure
        }
    }

    private fun creteRetrofitService(): MoviesApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val moshiConverterFactory = MoshiConverterFactory.create(moshi)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create(MoviesApiService::class.java)
    }
}