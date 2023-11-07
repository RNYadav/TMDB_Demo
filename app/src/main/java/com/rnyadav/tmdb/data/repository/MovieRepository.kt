package com.rnyadav.tmdb.data.repository

import com.rnyadav.tmdb.data.model.Movie
import com.rnyadav.tmdb.data.network.Resource


interface MovieRepository {
    suspend fun getPopularMovies(): Resource<List<Movie>>
    suspend fun getMovieDetails(id: Int): Resource<Movie>
}