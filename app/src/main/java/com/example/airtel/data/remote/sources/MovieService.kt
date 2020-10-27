package com.example.airtel.data.remote.sources

import com.example.airtel.data.remote.config.RestClient
import com.example.airtel.utils.Result
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL

interface MovieService {

    @GET("/")
    fun searchMovie(
        @Query("apikey") apiKey: String,
        @Query("s") query: String
    ): Single<MovieSearchResponse>

    @GET("/")
    fun getMovie(
        @Query("apikey") apiKey: String,
        @Query("i") movieId: String
    ): Single<MovieDetailResponse>
}

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