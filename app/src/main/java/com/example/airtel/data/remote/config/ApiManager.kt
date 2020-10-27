package com.example.airtel.data.remote.config

import com.example.airtel.data.remote.sources.MovieServiceNative
import com.example.airtel.data.remote.sources.MovieServiceNativeImpl
import com.google.gson.Gson

class RestManager(serializer: Gson) {
    private val restClient = RestClient(serializer)
    val movieService: MovieServiceNative by lazy { MovieServiceNativeImpl(restClient) }
}