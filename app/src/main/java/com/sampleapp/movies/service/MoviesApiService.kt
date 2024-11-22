package com.sampleapp.movies.service

import com.sampleapp.movies.model.MoviesApiResponse
import retrofit2.http.GET

interface MoviesApiService {
    @GET ("movie/popular")
    suspend fun getPopularMovies(): MoviesApiResponse
}