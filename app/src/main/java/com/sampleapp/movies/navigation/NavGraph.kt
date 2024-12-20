package com.sampleapp.movies.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sampleapp.movies.presentation.ui.screen.DetailsScreen
import com.sampleapp.movies.presentation.ui.screen.FavoritesScreen
import com.sampleapp.movies.presentation.ui.screen.MoviesScreen
import com.sampleapp.movies.presentation.viewmodel.MoviesViewModel

sealed class Screen(val route: String) {
    data object Movies : Screen("movies")
    data object Favorites : Screen("favorites")
    data object Details: Screen("details/{movieId}") {
        fun createRoute(movieId: Long): String = "details/$movieId"
    }
}

@Composable
fun NavGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "parent",
        modifier = Modifier.padding(innerPadding)
    ) {
        navigation(
            startDestination = Screen.Movies.route, route = "parent"
        ) {
            composable(Screen.Movies.route) { backStackEntry ->
                MoviesScreen(getParentViewModel(backStackEntry, navController)) { movieId ->
                    navigateToMovieDetails(movieId, navController)
                }
            }
            composable(Screen.Favorites.route) { backStackEntry ->
                FavoritesScreen(getParentViewModel(backStackEntry, navController)) { movieId ->
                    navigateToMovieDetails(movieId, navController)
                }
            }
            composable(Screen.Details.route) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toLong()
                DetailsScreen(getParentViewModel(backStackEntry, navController), movieId) {
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
private fun getParentViewModel(
    backStackEntry: NavBackStackEntry,
    navController: NavHostController
): MoviesViewModel {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry("parent")
    }
    return hiltViewModel<MoviesViewModel>(parentEntry)
}

private fun navigateToMovieDetails(movieId: Long, navController: NavHostController) {
    navController.navigate(Screen.Details.createRoute(movieId))
}