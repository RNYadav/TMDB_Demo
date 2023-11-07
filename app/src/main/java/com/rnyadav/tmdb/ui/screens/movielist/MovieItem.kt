package com.rnyadav.tmdb.ui.screens.movielist

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rnyadav.tmdb.R
import com.rnyadav.tmdb.data.model.Movie
import com.rnyadav.tmdb.ui.theme.TMDB_DemoTheme
import com.rnyadav.tmdb.ui.theme.spacing
import kotlinx.serialization.json.Json

@Composable
fun MovieItem(movie: Movie, navController: NavController
              ) {
    val spacing = MaterialTheme.spacing

        Row(
            modifier = Modifier
                .padding(spacing.small)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.fullPosterPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.movie_placeholder),
                contentDescription = movie.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.weight(0.4f).clip(RoundedCornerShape(spacing.medium)).clickable { navController.navigate("details/${movie.id}") }
            )
        }
}

//val movie = Json.decodeFromString<Movie>("{\"adult\":false,\"backdrop_path\":\"\\/y2Ca1neKke2mGPMaHzlCNDVZqsK.jpg\",\"genre_ids\":[28,35,53],\"id\":718930,\"original_language\":\"en\",\"original_title\":\"Bullet Train\",\"overview\":\"Unlucky assassin Ladybug is determined to do his job peacefully after one too many gigs gone off the rails. Fate, however, may have other plans, as Ladybug's latest mission puts him on a collision course with lethal adversaries from around the globe\\u2014all with connected, yet conflicting, objectives\\u2014on the world's fastest train.\",\"popularity\":5159.444,\"poster_path\":\"\\/tVxDe01Zy3kZqaZRNiXFGDICdZk.jpg\",\"release_date\":\"2022-07-03\",\"title\":\"Bullet Train\",\"video\":false,\"vote_average\":7.5,\"vote_count\":1407}")
