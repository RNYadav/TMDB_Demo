package com.rnyadav.tmdb.data.repository

import com.rnyadav.tmdb.data.model.Movie
import com.rnyadav.tmdb.data.model.PopularMovieList
import com.rnyadav.tmdb.data.network.HttpClient
import com.rnyadav.tmdb.data.network.Resource
import com.rnyadav.tmdb.utils.BASE_URL
import io.ktor.client.request.*
import javax.inject.Inject

private const val POPULAR_MOVIES = "${BASE_URL}/popular?region=IN"
private const val MOVIE_DETAILS = "${BASE_URL}/"


class MovieRepositoryImpl @Inject constructor(private val httpClient: HttpClient) : MovieRepository {

    override suspend fun getPopularMovies(): Resource<List<Movie>> {
        return try {
            Resource.Success(
                httpClient.getHttpClient().get<PopularMovieList> {
                    url(POPULAR_MOVIES)
                }.movies
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getMovieDetails(id: Int): Resource<Movie> {
        return try {
            Resource.Success(
                httpClient.getHttpClient().get {
                    url(MOVIE_DETAILS+id)
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}