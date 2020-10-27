package com.example.ch8n.search.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ch8n.data.repository.MovieRepositoryNative
import com.example.ch8n.search.MovieSearchViewModel

class MovieSearchViewModelFactory constructor(private val movieRepository: MovieRepositoryNative) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieSearchViewModel(movieRepository) as T
    }

}