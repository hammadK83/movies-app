package com.sampleapp.movies.data.remote

import com.sampleapp.movies.data.model.ConfigurationApiResponseModel
import com.sampleapp.movies.data.model.GenresApiResponseModel
import com.sampleapp.movies.data.model.MoviesApiResponseModel
import retrofit2.http.GET

const val BASE_URL = "https://api.themoviedb.org/3/"

interface MoviesApiService {
    @GET ("movie/popular")
    suspend fun getPopularMovies(): MoviesApiResponseModel

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationApiResponseModel

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresApiResponseModel
}