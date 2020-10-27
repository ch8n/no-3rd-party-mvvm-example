package com.example.ch8n.data.remote.config

import com.example.ch8n.data.remote.sources.MovieServiceNative
import com.example.ch8n.data.remote.sources.MovieServiceNativeImpl
import com.google.gson.Gson

class RestManager(serializer: Gson) {
    private val restClient = RestClient(serializer)
    val movieService: MovieServiceNative by lazy { MovieServiceNativeImpl(restClient) }
}