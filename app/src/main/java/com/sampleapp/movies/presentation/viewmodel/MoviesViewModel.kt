package com.sampleapp.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapp.movies.domain.model.Configuration
import com.sampleapp.movies.domain.usecase.GetConfigurationUseCase
import com.sampleapp.movies.domain.usecase.GetPopularMoviesUseCase
import com.sampleapp.movies.presentation.state.MoviesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getConfigurationUseCase: GetConfigurationUseCase
) : ViewModel() {
    private var configuration: Configuration? = null

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

    init {
        getConfiguration()
    }

    private fun getConfiguration() {
        viewModelScope.launch {
            configuration = getConfigurationUseCase.invoke()
        }
    }
}