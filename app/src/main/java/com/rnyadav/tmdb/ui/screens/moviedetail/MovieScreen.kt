package com.rnyadav.tmdb.ui.screens.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rnyadav.tmdb.MainViewModel
import com.rnyadav.tmdb.R
import com.rnyadav.tmdb.data.network.Resource
import com.rnyadav.tmdb.ui.common.FullScreenProgressbar
import com.rnyadav.tmdb.ui.common.toast
import com.rnyadav.tmdb.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    id: Int, navController: NavController = rememberNavController(),
    viewModel: MainViewModel = viewModel()
) {
    val spacing = MaterialTheme.spacing
    var loaded by remember { mutableStateOf("") }
    if(loaded.isEmpty()) {
        viewModel.getMovieDetails(id)
        loaded = "loading"
    }
    val details = viewModel.movie.collectAsState()
    details.value?.let {
        when (it) {
            is Resource.Failure -> {
                navController.context.toast(it.exception.message!!)
            }

            Resource.Loading -> {
                FullScreenProgressbar()
            }

            is Resource.Success -> {
                val movie = it.result
                Box {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            AsyncImage(
                                modifier = Modifier.fillMaxWidth(),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(movie.fullPosterPath)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.movie_placeholder),
                                contentDescription = movie.title,
                                contentScale = ContentScale.Fit
                            )
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .padding(start = spacing.medium, end = spacing.medium)
                            ) {
                                Spacer(modifier = Modifier.size(spacing.extraSmall))

                                Text(
                                    text = movie.originalTitle,
                                    style = MaterialTheme.typography.displaySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )

                                Spacer(modifier = Modifier.size(spacing.small))

                                Row(
                                    Modifier.align(Alignment.CenterHorizontally)
                                ) {

                                    Text(
                                        text = movie.originalLanguage,
                                    )
                                    Spacer(modifier = Modifier.size(spacing.small))
                                    Text(
                                        text = "IMDB ${movie.voteAverage}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(spacing.extraSmall))
                                            .background(Color.Yellow)
                                            .padding(
                                                start = spacing.small,
                                                end = spacing.small,
                                                top = spacing.extraSmall,
                                                bottom = spacing.extraSmall
                                            )
                                    )
                                    Spacer(modifier = Modifier.size(spacing.small))
                                    Text(
                                        text = " (${movie.voteCount} Reviews)",
                                    )
                                }


                                Spacer(modifier = Modifier.size(spacing.medium))

                                Text(
                                    text = "Overview",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.size(spacing.small))
                                Text(
                                    text = movie.overview,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 7,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.size(spacing.xxLarge))

                            }
                        }
                    }
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigate("home") {
                                    popUpToRoute
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                        },
                        title = {
                            Text("")
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
                        actions = {
                            IconButton(onClick = { navController.context.toast("Future Implementation") }) {
                                Icon(
                                    imageVector = Icons.Filled.FavoriteBorder,
                                    contentDescription = "Localized description"
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}