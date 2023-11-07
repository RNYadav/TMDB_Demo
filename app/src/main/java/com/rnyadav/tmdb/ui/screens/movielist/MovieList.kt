package com.rnyadav.tmdb.ui.screens.movielist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.rnyadav.tmdb.data.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(movies: List<Movie>, navController: NavController) {
    val listState = rememberLazyListState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Popular Movies")
                },
            )
        }
    ) {
        innerPadding -> Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(state = listState) {
                items(movies) { item ->
                    MovieItem(item, navController)
                }
            }
        }
    }
}