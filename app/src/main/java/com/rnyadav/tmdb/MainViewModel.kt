package com.rnyadav.tmdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rnyadav.tmdb.data.model.Movie
import com.rnyadav.tmdb.data.network.Resource
import com.rnyadav.tmdb.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<List<Movie>>?>(null)
    val movies: StateFlow<Resource<List<Movie>>?> = _movies

    private val _movie = MutableStateFlow<Resource<Movie>?>(null)
    val movie: StateFlow<Resource<Movie>?> = _movie

    init {
        viewModelScope.launch {
            _movies.value = Resource.Loading
            _movies.value = repository.getPopularMovies()
        }
    }

    fun getMovieDetails(id: Int){
        viewModelScope.launch {
            _movie.value = Resource.Loading
            _movie.value = repository.getMovieDetails(id)
        }
    }
}