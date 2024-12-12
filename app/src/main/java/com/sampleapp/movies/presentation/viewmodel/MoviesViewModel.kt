package com.sampleapp.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapp.movies.domain.model.Configuration
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.domain.usecase.AddFavoriteUseCase
import com.sampleapp.movies.domain.usecase.GetConfigurationUseCase
import com.sampleapp.movies.domain.usecase.GetFavoriteByIdUseCase
import com.sampleapp.movies.domain.usecase.GetPopularMoviesUseCase
import com.sampleapp.movies.domain.usecase.RemoveFavoriteUseCase
import com.sampleapp.movies.presentation.state.MoviesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getConfigurationUseCase: GetConfigurationUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val getFavoriteByIdUseCase: GetFavoriteByIdUseCase
) : ViewModel() {
    private val moviesListStateMutable = MutableStateFlow<MoviesListState>(MoviesListState.Loading)
    val moviesListState = moviesListStateMutable.asStateFlow()

    private var configuration: Configuration? = null

    fun fetchPopularMovies() {
        viewModelScope.launch {
            moviesListStateMutable.value = MoviesListState.Loading
            val result = getPopularMoviesUseCase.invoke()
            result.fold(
                onSuccess = { movies ->
                    movies.forEach { it.setIsFavorite(getFavoriteByIdUseCase) }
                    moviesListStateMutable.value = MoviesListState.Loaded(movies)
                },
                onFailure = {
                    moviesListStateMutable.value = MoviesListState.Failed
                }
            )
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                removeFavoriteUseCase.invoke(movie)
            } else {
                addFavoriteUseCase.invoke(movie)
            }
        }
        val currentState = moviesListStateMutable.value
        if (currentState is MoviesListState.Loaded) {
            moviesListStateMutable.value = MoviesListState.Loaded(
                currentState.movies.map {
                    if (it.id == movie.id) it.copy(isFavorite = !it.isFavorite) else it
                }
            )
        }
    }

    fun createImageUrl(imagePath: String?) = configuration?.imageBaseUrl?.let {
        "$it/w500/$imagePath"
    }

    private fun getConfiguration() {
        viewModelScope.launch {
            configuration = getConfigurationUseCase.invoke()
        }
    }

    init {
        getConfiguration()
    }
}