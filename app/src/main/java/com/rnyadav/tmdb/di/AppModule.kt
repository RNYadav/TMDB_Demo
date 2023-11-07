package com.rnyadav.tmdb.di

import com.rnyadav.tmdb.data.repository.MovieRepository
import com.rnyadav.tmdb.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun getHttpClient(httpClient: HttpClient): HttpClient = httpClient

    @Provides
    fun getMoviesRepository(impl: MovieRepositoryImpl): MovieRepository = impl
}