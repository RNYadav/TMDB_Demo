package com.rnyadav.tmdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rnyadav.tmdb.data.model.Movie
import com.rnyadav.tmdb.data.network.Resource
import com.rnyadav.tmdb.ui.common.FullScreenProgressbar
import com.rnyadav.tmdb.ui.common.toast
import com.rnyadav.tmdb.ui.screens.moviedetail.MovieScreen
import com.rnyadav.tmdb.ui.screens.movielist.HomeScreen
import com.rnyadav.tmdb.ui.theme.TMDB_DemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val movies = viewModel.movies.collectAsState()
            val navController = rememberNavController()

            TMDB_DemoTheme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        movies.value?.let {
                            when (it) {
                                is Resource.Failure -> {
                                    context.toast(it.exception.message!!)
                                }

                                is Resource.Loading -> {
                                    FullScreenProgressbar()
                                }

                                is Resource.Success -> {
                                    HomeScreen(it.result, navController)
                                }
                            }
                        }
                    }
                    composable("details/{movieId}") { navBackStackEntry ->
                        /* Extracting the id from the route */
                        val carId = navBackStackEntry.arguments?.getString("movieId")
                        /* We check if id is null */
                        carId?.let {
                            MovieScreen(it.toInt(), navController, viewModel)
                        }
                    }
                }

            }
        }
    }
}
