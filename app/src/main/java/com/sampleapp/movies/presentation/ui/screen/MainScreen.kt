package com.sampleapp.movies.presentation.ui.screen

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sampleapp.movies.R
import com.sampleapp.movies.navigation.NavGraph
import com.sampleapp.movies.navigation.Screen
import com.sampleapp.movies.util.Constants

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val shouldShowAppBar = currentDestination != Screen.Details.route
    Scaffold(
        topBar = {
            if (shouldShowAppBar) {
                AppTopBar(navController)
            }
        }
    ) { innerPadding ->
        NavGraph(navController = navController, innerPadding = innerPadding)
    }
}

@Composable
private fun AppTopBar(navController: NavHostController) {
    val tabs = listOf(Screen.Movies, Screen.Favorites)
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    TabRow(
        selectedTabIndex = tabs.indexOfFirst { it.route == currentRoute }
            .takeIf { it >= 0 } ?: 0
    ) {
        tabs.forEachIndexed { index, screen ->
            Tab(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                text = { Text(getTabName(index)) }
            )
        }
    }
}

@Composable
private fun getTabName(position: Int): String =
    if (position == Constants.TAB_ORDER_MOVIES_SCREEN) {
        stringResource(R.string.movies_screen_name)
    } else {
        stringResource(R.string.favorites_screen_name)
    }