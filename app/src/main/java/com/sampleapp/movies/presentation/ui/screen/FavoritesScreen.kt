package com.sampleapp.movies.presentation.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sampleapp.movies.R

@Composable
fun FavoritesScreen() {
    Text(stringResource(R.string.favorites_screen_empty_list_text))
}