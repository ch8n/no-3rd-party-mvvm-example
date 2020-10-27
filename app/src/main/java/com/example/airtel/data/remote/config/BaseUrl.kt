package com.example.airtel.data.remote.config

object BaseUrl {
    val dev = "http://www.omdbapi.com"
}

enum class API(val endPoint: String) {
    SEARCH("http://www.omdbapi.com"),
    MOVIES("http://www.omdbapi.com")
}

