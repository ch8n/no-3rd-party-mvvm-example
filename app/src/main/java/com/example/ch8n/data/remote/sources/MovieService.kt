package com.example.ch8n.data.remote.sources

import com.example.ch8n.data.remote.config.RestClient
import com.example.ch8n.utils.Result
import java.net.URL

interface MovieServiceNative {
    suspend fun searchMovie(requestUrl: URL): Result<Exception, MovieSearchResponse>
    suspend fun getMovie(requestUrl: URL): Result<Exception, MovieDetailResponse>
}

class MovieServiceNativeImpl(private val restClient: RestClient) : MovieServiceNative {

    override suspend fun searchMovie(requestUrl: URL): Result<Exception, MovieSearchResponse> {
        return restClient.get(requestUrl)
    }

    override suspend fun getMovie(requestUrl: URL): Result<Exception, MovieDetailResponse> {
        return restClient.get(requestUrl)
    }

}