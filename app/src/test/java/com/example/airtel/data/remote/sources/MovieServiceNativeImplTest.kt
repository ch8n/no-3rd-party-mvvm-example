package com.example.airtel.data.remote.sources

import com.example.airtel.data.remote.config.RestClient
import com.example.airtel.data.remote.config.UrlBuilder
import com.example.airtel.utils.Result
import com.google.common.truth.Truth
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException

class MovieServiceNativeImplTest {

    lateinit var restClient: RestClient
    lateinit var movieService: MovieServiceNativeImpl

    @Before
    fun setup() {
        restClient = RestClient(Gson()) // this depends on Java URL class and cannot be mocked
        movieService = MovieServiceNativeImpl(restClient)
    }

    @Test
    fun `searchMovie when success return result response`() = runBlocking {
        val requestUrl = UrlBuilder("http://www.omdbapi.com")
            .addQueryParam("apikey" to "b6573989")
            .addQueryParam("s" to "pokemon")
            .build()
        val response = movieService.searchMovie(requestUrl)
        Truth.assertThat(response).isInstanceOf(Result.Success::class.java)
        Truth.assertThat((response as Result.Success).value.response?.toBoolean()).isEqualTo(true)
        Truth.assertThat((response as Result.Success).value.totalResults).isNotEmpty()
    }

    @Test
    fun `searchMovie when failed return result error of IO`() = runBlocking {


    }

    @Test
    fun getMovie() {
    }
}