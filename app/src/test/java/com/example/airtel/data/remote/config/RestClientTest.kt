package com.example.airtel.data.remote.config

import com.example.airtel.data.remote.sources.MovieSearchResponse
import com.example.airtel.utils.Result
import com.google.common.truth.Truth
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

class RestClientTest {

    @Test
    fun `Rest Client gives error response`() = runBlocking {
        val restProvider = RestClient(Gson())
        val requestUrl = UrlBuilder("http://www.omdbapi.com")
            .addQueryParam("apikey" to "")
            .addQueryParam("s" to "pokemon")
            .build()
        val response = restProvider.get<MovieSearchResponse>(requestUrl)
        Truth.assertThat(response).isInstanceOf(Result.Error::class.java)
        Truth.assertThat((response as Result.Error).error).isInstanceOf(IOException::class.java)
        Truth.assertThat((response as Result.Error).error.message).isNotEmpty()
    }

    @Test
    fun `Rest Client gives success response`() = runBlocking {
        val restProvider = RestClient(Gson())
        val requestUrl = UrlBuilder("http://www.omdbapi.com")
            .addQueryParam("apikey" to "b6573989")
            .addQueryParam("s" to "pokemon")
            .build()
        val response = restProvider.get<MovieSearchResponse>(requestUrl)
        Truth.assertThat(response).isInstanceOf(Result.Success::class.java)
        Truth.assertThat((response as Result.Success).value).isInstanceOf(MovieSearchResponse::class.java)
        Truth.assertThat((response as Result.Success).value.search).isNotEmpty()
    }
}