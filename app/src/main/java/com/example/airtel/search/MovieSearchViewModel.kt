package com.example.airtel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airtel.data.remote.sources.MovieSearchRequest
import com.example.airtel.data.repository.MovieRepositoryNative
import com.example.airtel.search.adapter.SearchListItem
import com.example.airtel.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieSearchViewModel(private val movieRespository: MovieRepositoryNative) : ViewModel() {

    private val _searchMovieLiveData = MutableLiveData<List<SearchListItem>>()
    private val _errorLiveData = MutableLiveData<Throwable>()

    val errorLiveData: LiveData<Throwable> = _errorLiveData
    val searchMovieLiveData: LiveData<List<SearchListItem>> = _searchMovieLiveData

    fun searchMovieNative(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieRespository.searchMovie(MovieSearchRequest(query))
            when (result) {
                is Result.Success -> {
                    val viewList = result.value.search?.map { movieDetail ->
                        SearchListItem(
                            imageUrl = movieDetail.poster ?: "",
                            movieTitle = movieDetail?.title ?: "",
                            movieReleaseYear = movieDetail.year ?: "",
                            movieId = movieDetail.imdbID ?: "",
                            movieType = movieDetail.type ?: ""
                        )
                    } ?: emptyList()
                    _searchMovieLiveData.postValue(viewList)
                }
                is Result.Error -> {
                    _errorLiveData.postValue(result.error)
                }
            }
        }
    }

}