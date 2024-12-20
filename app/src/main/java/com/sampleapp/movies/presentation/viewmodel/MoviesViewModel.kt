package com.sampleapp.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapp.movies.domain.model.Configuration
import com.sampleapp.movies.domain.model.Genre
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.domain.usecase.AddFavoriteUseCase
import com.sampleapp.movies.domain.usecase.FormatDateUseCase
import com.sampleapp.movies.domain.usecase.GetConfigurationUseCase
import com.sampleapp.movies.domain.usecase.GetFavoritesUseCase
import com.sampleapp.movies.domain.usecase.GetGenresUseCase
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
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val formatDateUseCase: FormatDateUseCase
) : ViewModel() {
    private val moviesListStateMutable = MutableStateFlow<MoviesListState>(MoviesListState.Loading)
    val moviesListState = moviesListStateMutable.asStateFlow()

    private val favoriteMoviesStateMutable =
        MutableStateFlow<MoviesListState>(MoviesListState.Loading)
    val favoriteMoviesState = favoriteMoviesStateMutable.asStateFlow()

    private var configuration: Configuration? = null

    private var genres: List<Genre>? = null

    fun fetchPopularMovies() {
        viewModelScope.launch {
            moviesListStateMutable.value = MoviesListState.Loading
            val result = getPopularMoviesUseCase.invoke()
            result.fold(
                onSuccess = { movies ->
                    setFavoriteState(movies)
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
        updateFavoritesInMoviesList(movie, !movie.isFavorite)
        removeMovieFromFavoritesList(movie)
    }

    fun getAllFavoriteMovies() {
        viewModelScope.launch {
            val result = getFavoritesUseCase.invoke()
            if (result.isEmpty()) {
                favoriteMoviesStateMutable.value = MoviesListState.Failed
            } else {
                favoriteMoviesStateMutable.value = MoviesListState.Loaded(result)
            }
        }
    }

    fun getMovieById(id: Long?) =
        (moviesListState.value as? MoviesListState.Loaded)?.movies?.firstOrNull {
            it.id == id
        } ?: (favoriteMoviesState.value as? MoviesListState.Loaded)?.movies?.firstOrNull()

    fun createPosterImageUrl(imagePath: String?) = configuration?.imageBaseUrl?.let {
        "$it/w342/$imagePath"
    }

    fun createBackdropImageUrl(imagePath: String?) = configuration?.imageBaseUrl?.let {
        "$it/w780/$imagePath"
    }

    fun getGenres(genreIds: List<Int>): List<String> {
        val genresList = mutableListOf<String>()
        genres?.forEach {
            if (it.id in genreIds) {
                genresList.add(it.name)
            }
        }
        return genresList
    }

    fun formatDate(inputDate: String?): String = formatDateUseCase.invoke(inputDate)

    private fun removeMovieFromFavoritesList(movie: Movie) {
        val currentState = favoriteMoviesState.value
        if (currentState is MoviesListState.Loaded) {
            favoriteMoviesStateMutable.value = MoviesListState.Loaded(
                currentState.movies.map {
                    if (it.id == movie.id) it.copy(isFavorite = false) else it
                }
            )
        }
    }

    private fun updateFavoritesInMoviesList(movie: Movie, isFavorite: Boolean) {
        val currentState = moviesListState.value
        if (currentState is MoviesListState.Loaded) {
            moviesListStateMutable.value = MoviesListState.Loaded(
                currentState.movies.map {
                    if (it.id == movie.id) it.copy(isFavorite = isFavorite) else it
                }
            )
        }
    }

    private fun setFavoriteState(movies: List<Movie>) {
        viewModelScope.launch {
            val favoriteMovies = getFavoritesUseCase.invoke()
            movies.forEach { movie ->
                if (favoriteMovies.any { movie.id == it.id }) {
                    movie.isFavorite = true
                }
            }
        }
    }

    private fun getConfiguration() {
        viewModelScope.launch {
            configuration = getConfigurationUseCase.invoke()
        }
    }

    private fun getGenres() {
        viewModelScope.launch {
            genres = getGenresUseCase.invoke()
        }
    }

    init {
        getConfiguration()
        getGenres()
    }
}