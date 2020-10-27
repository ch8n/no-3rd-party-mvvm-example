package com.example.airtel.data.remote.config

import com.example.airtel.data.remote.sources.*
import com.example.airtel.utils.Result
import com.google.gson.Gson
import retrofit2.Retrofit

class ApiManager(private val retrofit: Retrofit) {
    val movieService: MovieService by lazy { retrofit.prepare<MovieService>() }
    inline fun <reified T> Retrofit.prepare(): T {
        return this.create(T::class.java)
    }
}

class RestManager(serializer: Gson) {
    private val restClient = RestClient(serializer)
    val movieService: MovieServiceNative by lazy { MovieServiceNativeImpl(restClient) }
}