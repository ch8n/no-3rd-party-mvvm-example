package com.example.ch8n.data.repository

import com.example.ch8n.data.remote.sources.*
import com.example.ch8n.utils.Result
import kotlin.Exception


class MovieRepositoryNative(private val movieSource : MovieSourceNative) {

    suspend fun searchMovie(searchRequest: MovieSearchRequest): Result<Exception,MovieSearchResponse> {
        return movieSource.searchMovie(searchRequest)
    }

    suspend fun getMovieDetail(movieDetailRequest: MovieDetailRequest): Result<Exception,MovieDetailResponse> {
        return movieSource.getMovieDetail(movieDetailRequest.movieId)
    }
}

