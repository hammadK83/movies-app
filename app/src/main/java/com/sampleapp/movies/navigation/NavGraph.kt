package com.sampleapp.movies.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sampleapp.movies.presentation.ui.screen.FavoritesScreen
import com.sampleapp.movies.presentation.ui.screen.MoviesScreen

sealed class Screen(val route: String) {
    data object Movies : Screen("movies")
    data object Favorites : Screen("favorites")
}

@Composable
fun NavGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.Movies.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Movies.route) { MoviesScreen() }
        composable(Screen.Favorites.route) { FavoritesScreen() }
    }
}