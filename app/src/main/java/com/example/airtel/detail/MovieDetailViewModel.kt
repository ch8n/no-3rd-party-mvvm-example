package com.example.airtel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airtel.data.remote.sources.MovieDetailRequest
import com.example.airtel.data.remote.sources.MovieDetailResponse
import com.example.airtel.data.remote.sources.Rating
import com.example.airtel.data.repository.MovieRepositoryNative
import com.example.airtel.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieRespository: MovieRepositoryNative) : ViewModel() {

    private val _ratingLiveData = MutableLiveData<MovieDetailResponse>()
    private val _errorLiveData = MutableLiveData<Throwable>()

    val errorLiveData: LiveData<Throwable> = _errorLiveData
    val ratingLiveData: LiveData<MovieDetailResponse> = _ratingLiveData

    fun getMovieDetailNative(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieRespository.getMovieDetail(MovieDetailRequest(movieId))
            when (result) {
                is Result.Success -> {
                    val viewList = result.value.ratings?.map {
                        val rating = "${it.source} : ${it.value}"
                        Rating(source = rating, value = null)
                    } ?: emptyList()
                    _ratingLiveData.postValue(result.value)
                }
                is Result.Error -> {
                    _errorLiveData.postValue(result.error)
                }
            }
        }
    }
}