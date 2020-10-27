package com.example.airtel.detail.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.airtel.data.repository.MovieRepositoryNative
import com.example.airtel.detail.MovieDetailViewModel


class MovieDetailViewModelFactory constructor(private val movieRepository: MovieRepositoryNative) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(movieRepository) as T

    }

}