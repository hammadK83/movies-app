package com.sampleapp.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapp.movies.domain.usecase.GetPopularMoviesUseCase
import com.sampleapp.movies.presentation.state.MoviesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    private val moviesListStateMutable = MutableStateFlow<MoviesListState>(MoviesListState.Loading)
    val moviesListState = moviesListStateMutable.asStateFlow()

    fun fetchPopularMovies() {
        viewModelScope.launch {
            moviesListStateMutable.value = MoviesListState.Loading
            val result = getPopularMoviesUseCase.invoke()
            result.fold(
                onSuccess = { movies ->
                    moviesListStateMutable.value = MoviesListState.Loaded(movies)
                },
                onFailure = {
                    moviesListStateMutable.value = MoviesListState.Failed
                }
            )
        }
    }
}