package com.sampleapp.movies.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.sampleapp.movies.R
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.presentation.state.MoviesListState
import com.sampleapp.movies.presentation.viewmodel.MoviesViewModel

@Composable
fun MoviesScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    val state = viewModel.moviesListState.collectAsState().value

    if (state == MoviesListState.Loading) {
        viewModel.fetchPopularMovies()
    }

    when (state) {
        is MoviesListState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        is MoviesListState.Loaded -> {
            MoviesList(state.movies, viewModel)
        }

        is MoviesListState.Failed -> {
            Text(text = "Failed to load movies", modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun MoviesList(movies: List<Movie>, viewModel: MoviesViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies.size) { index ->
            MovieItem(movie = movies[index], viewModel)
        }
    }
}

@Composable
fun MovieItem(movie: Movie, viewModel: MoviesViewModel) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    model = viewModel.createImageUrl(movie.posterPath),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(R.string.content_desc_movie_poster),
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(36.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.onPrimary, CircleShape), // Circular white background
                    contentAlignment = Alignment.Center // Center-align the icon within the circle
                ) {
                    IconButton(
                        onClick = { /* TODO: Handle click */ },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder, // Replace with your heart icon
                            contentDescription = null, // TODO: Update this based on the favorite state
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Text(
                text = movie.title.orEmpty(),
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}