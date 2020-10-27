package com.example.airtel.di

import androidx.appcompat.app.AppCompatActivity
import com.example.airtel.detail.MovieDetailViewModel
import com.example.airtel.search.MovieSearchViewModel
import com.google.gson.Gson

object Injector {

    private val apiKey = Resolver.provideApiKey()
    private val gson = Gson()

    private val restManager = Resolver.provideRestManager(gson)

    private val movieServiceProvider = Resolver.provideMovieServiceProvider(apiKey, restManager)

    private val movieSource by lazy {
        Resolver.provideMovieDataSource(movieServiceProvider)
    }

    private val movieRepository by lazy {
        Resolver.provideMovieRepository(movieSource)
    }

    private val searchViewModelFactory by lazy {
        Resolver.provideSearchViewModelFactory(movieRepository)
    }

    fun searchViewModel(activity: AppCompatActivity) : MovieSearchViewModel {
        return Resolver.provideSearchViewModel(searchViewModelFactory, activity)
    }

    private val detailViewModelFactory by lazy {
        Resolver.provideDetailViewModelFactory(movieRepository)
    }

    fun detailViewModel(activity: AppCompatActivity) : MovieDetailViewModel {
        return Resolver.provideDetailViewModel(detailViewModelFactory, activity)
    }
}
