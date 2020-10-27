package com.example.airtel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airtel.data.remote.sources.MovieDetailRequest
import com.example.airtel.data.remote.sources.MovieDetailResponse
import com.example.airtel.data.remote.sources.MovieSearchRequest
import com.example.airtel.data.remote.sources.Rating
import com.example.airtel.data.repository.MovieRepository
import com.example.airtel.data.repository.MovieRepositoryNative
import com.example.airtel.di.Injector
import com.example.airtel.search.adapter.SearchListItem
import com.example.airtel.utils.Result
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MovieDetailViewModel(private val movieRespository: MovieRepositoryNative) : ViewModel() {

    private val _ratingLiveData = MutableLiveData<List<Rating>>()
    private val _errorLiveData = MutableLiveData<Throwable>()

    val errorLiveData: LiveData<Throwable> = _errorLiveData
    val ratingLiveData: LiveData<List<Rating>> = _ratingLiveData

    fun getMovieDetailNative(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieRespository.getMovieDetail(MovieDetailRequest(movieId))
            when (result) {
                is Result.Success -> {
                    val viewList = result.value.ratings?.map {
                        val rating = "${it.source} : ${it.value}"
                        Rating(source = rating, value = null)
                    } ?: emptyList()
                    _ratingLiveData.postValue(viewList)
                }
                is Result.Error -> {
                    _errorLiveData.postValue(result.error)
                }
            }
        }
    }
}