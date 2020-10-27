package com.example.ch8n.data.remote.sources

import com.example.ch8n.data.remote.config.API
import com.example.ch8n.data.remote.config.UrlBuilder
import com.example.ch8n.utils.Result

class MovieSourceNative(val movieServiceProvider: MovieServiceProvider) {

    suspend fun searchMovie(query: MovieSearchRequest): Result<Exception, MovieSearchResponse> {
        return movieServiceProvider.searchMovie(query.query)
    }

    suspend fun getMovieDetail(movieId: String): Result<Exception, MovieDetailResponse> {
        return movieServiceProvider.getMovieDetail(movieId)
    }
}


class MovieServiceProvider(val serviceAuthKey: String, val movieService: MovieServiceNative) {

    suspend fun searchMovie(query: String): Result<Exception, MovieSearchResponse> {
        val requestBuilder = UrlBuilder(API.MOVIES.endPoint)
            .addQueryParam("apikey" to serviceAuthKey)
            .addQueryParam("s" to query)
            .build()
        return movieService.searchMovie(requestBuilder)
    }

    suspend fun getMovieDetail(movieId: String): Result<Exception, MovieDetailResponse> {
        val requestBuilder = UrlBuilder(API.MOVIES.endPoint)
            .addQueryParam("apikey" to serviceAuthKey)
            .addQueryParam("i" to movieId)
            .build()
        return movieService.getMovie(requestBuilder)
    }
}