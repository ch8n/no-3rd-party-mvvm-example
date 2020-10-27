package com.example.airtel.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.airtel.data.remote.config.ApiManager
import com.example.airtel.data.remote.config.RestClient
import com.example.airtel.data.remote.config.RestManager
import com.example.airtel.data.remote.sources.MovieServiceProvider
import com.example.airtel.data.remote.sources.MovieSource
import com.example.airtel.data.remote.sources.MovieSourceNative
import com.example.airtel.data.repository.MovieRepository
import com.example.airtel.data.repository.MovieRepositoryNative
import com.example.airtel.detail.MovieDetailViewModel
import com.example.airtel.detail.factory.MovieDetailViewModelFactory
import com.example.airtel.search.MovieSearchViewModel
import com.example.airtel.search.factories.MovieSearchViewModelFactory
import com.google.gson.Gson

object Resolver {

    fun provideRestManager(gson: Gson) = RestManager(gson)

    fun provideApiKey(): String = "b6573989"

    fun provideMovieServiceProvider(
        apiKey: String,
        restManager: RestManager
    ): MovieServiceProvider = MovieServiceProvider(apiKey, restManager.movieService)

    fun provideMovieDataSource(movieServiceProvider: MovieServiceProvider): MovieSourceNative =
        MovieSourceNative(movieServiceProvider)

    fun provideMovieRepository(movieSource: MovieSourceNative): MovieRepositoryNative =
        MovieRepositoryNative(movieSource)

    fun provideSearchViewModelFactory(movieRepository: MovieRepositoryNative) =
        MovieSearchViewModelFactory(movieRepository)

    fun provideSearchViewModel(factory: MovieSearchViewModelFactory, activity: FragmentActivity) =
        ViewModelProviders
            .of(activity, factory)
            .get(MovieSearchViewModel::class.java)

    fun provideDetailViewModelFactory(movieRepository: MovieRepositoryNative) =
        MovieDetailViewModelFactory(movieRepository)

    fun provideDetailViewModel(factory: MovieDetailViewModelFactory, activity: FragmentActivity) =
        ViewModelProviders
            .of(activity, factory)
            .get(MovieDetailViewModel::class.java)

}