package com.example.airtel.data.repository

import com.example.airtel.data.remote.sources.*
import com.example.airtel.utils.Result
import io.reactivex.Single
import kotlin.Exception

class MovieRepository(private val movieSource : MovieSource) {

    fun searchMovie(searchRequest: MovieSearchRequest): Single<MovieSearchResponse> {
        return movieSource.searchMovie(searchRequest.query)
    }

    fun getMovieDetail(movieDetailRequest: MovieDetailRequest): Single<MovieDetailResponse> {
        return movieSource.getMovie(movieDetailRequest.movieId)
    }
}

class MovieRepositoryNative(private val movieSource : MovieSourceNative) {

    suspend fun searchMovie(searchRequest: MovieSearchRequest): Result<Exception,MovieSearchResponse> {
        return movieSource.searchMovie(searchRequest)
    }

    suspend fun getMovieDetail(movieDetailRequest: MovieDetailRequest): Result<Exception,MovieDetailResponse> {
        return movieSource.getMovieDetail(movieDetailRequest.movieId)
    }
}

