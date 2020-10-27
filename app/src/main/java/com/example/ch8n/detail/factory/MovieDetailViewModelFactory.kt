package com.example.ch8n.detail.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ch8n.data.repository.MovieRepositoryNative
import com.example.ch8n.detail.MovieDetailViewModel


class MovieDetailViewModelFactory constructor(private val movieRepository: MovieRepositoryNative) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(movieRepository) as T

    }

}