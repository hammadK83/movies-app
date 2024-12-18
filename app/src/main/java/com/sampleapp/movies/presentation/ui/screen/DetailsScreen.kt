package com.sampleapp.movies.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.sampleapp.movies.R
import com.sampleapp.movies.domain.model.Movie
import com.sampleapp.movies.presentation.ui.shared.getFavoriteIconContentDesc
import com.sampleapp.movies.presentation.viewmodel.MoviesViewModel
import com.sampleapp.movies.util.getFavoriteIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(viewModel: MoviesViewModel, movieId: Long?, onNavigateUp: () -> Unit) {
    val movie = viewModel.getMovieById(movieId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.details_screen_top_bar_title)) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_desc_up_navigation)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        if (movie == null) {
            ErrorText(contentPadding)
        } else {
            MovieDetails(movie, viewModel, contentPadding)
        }
    }
}

@Composable
fun ErrorText(contentPadding: PaddingValues) =
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.details_screen_error_text),
            fontSize = 18.sp,
            modifier = Modifier.padding(contentPadding)
        )
    }

@Composable
fun MovieDetails(movie: Movie, viewModel: MoviesViewModel, contentPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Constraints
            val (backdropImage, tint, posterImage, contentColumn) = createRefs()

            BackdropImage(
                movie,
                viewModel,
                Modifier.constrainAs(backdropImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.percent(0.3f)
                }
            )

            // Backdrop tint
            Box(
                modifier = Modifier
                    .constrainAs(tint) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.percent(0.3f)
                    }
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            val topGuideline = createGuidelineFromTop(0.1f)

            PosterImage(
                movie,
                viewModel,
                Modifier.constrainAs(posterImage) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.5f)
                    height = Dimension.wrapContent
                }
            )

            Column(
                modifier = Modifier
                    .constrainAs(contentColumn) {
                        top.linkTo(posterImage.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                TitleRow(movie, viewModel)

                Spacer(modifier = Modifier.height(8.dp))

                // Release Date
                Text(
                    text = stringResource(
                        R.string.released_prefix,
                        viewModel.formatDate(movie.releaseDate)
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                GenresLayout(movie, viewModel)

                Spacer(modifier = Modifier.height(16.dp))

                // Overview Title
                Text(
                    text = stringResource(R.string.overview_section_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Movie Overview
                Text(
                    text = movie.overview.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
fun BackdropImage(movie: Movie, viewModel: MoviesViewModel, modifier: Modifier) =
    AsyncImage(
        model = viewModel.createBackdropImageUrl(movie.backdropPath),
        contentDescription = stringResource(R.string.content_desc_movie_backdrop),
        contentScale = ContentScale.Crop,
        modifier = modifier,
        placeholder = painterResource(R.drawable.backdrop_image_placeholder),
        error = painterResource(R.drawable.backdrop_image_placeholder),
        fallback = painterResource(R.drawable.backdrop_image_placeholder),
    )

@Composable
fun PosterImage(movie: Movie, viewModel: MoviesViewModel, modifier: Modifier) =
    AsyncImage(
        model = viewModel.createPosterImageUrl(movie.posterPath),
        contentDescription = stringResource(R.string.content_desc_movie_poster),
        modifier = modifier
            .aspectRatio(2f / 3f)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.poster_image_placeholder),
        error = painterResource(R.drawable.poster_image_placeholder),
        fallback = painterResource(R.drawable.poster_image_placeholder)
    )

@Composable
fun TitleRow(movie: Movie, viewModel: MoviesViewModel) =
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isFavorite = remember { mutableStateOf(movie.isFavorite) }

        // Movie Name
        Text(
            modifier = Modifier
                .weight(1f) // Take up available space
                .padding(end = 16.dp),
            text = movie.title.orEmpty(),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        // Vertical divider
        VerticalDivider(
            color = Color.Gray.copy(alpha = 0.5f),
            modifier = Modifier
                .height(40.dp) // Height of the divider
                .width(1.dp) // Thickness of the divider
        )

        Spacer(Modifier.width(8.dp))

        IconButton(
            onClick = {
                isFavorite.value = !isFavorite.value
                onFavoriteClicked(movie, viewModel)
            },
        ) {
            Icon(
                imageVector = getFavoriteIcon(isFavorite.value),
                contentDescription = getFavoriteIconContentDesc(isFavorite.value),
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenresLayout(movie: Movie, viewModel: MoviesViewModel) =
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxLines = 2
    ) {
        val genres = viewModel.getGenres(movie.genreIds.orEmpty())
        genres.forEach { genre ->
            Text(
                text = genre,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondary,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                color = Color.White
            )
        }
    }

private fun onFavoriteClicked(movie: Movie, viewModel: MoviesViewModel) {
    viewModel.toggleFavorite(movie)
}
