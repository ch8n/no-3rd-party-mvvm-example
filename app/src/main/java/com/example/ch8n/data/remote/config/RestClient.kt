package com.example.ch8n.data.remote.config

import com.example.ch8n.utils.Result
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class UrlBuilder(private val requestUrl: String) {

    private val queryParams = mutableMapOf<String, String>()

    fun addQueryParam(param: Pair<String, String>): UrlBuilder {
        queryParams.put(param.first, param.second)
        return this
    }

    fun build(): URL {
        val queryString = queryParams.entries.joinToString(separator = "&") { (key, value) ->
            "${key}=${value}"
        }
        val urlwithQuery = if (queryString.isEmpty()) {
            requestUrl
        } else {
            "$requestUrl/?$queryString"
        }
        return URL(urlwithQuery)
    }
}

class RestClient(val serializer: Gson) {

    inline fun <reified V> get(url: URL): Result<Exception, V> {
        return Result.build {
            val urlConnection = url.openConnection() as? HttpURLConnection
            urlConnection ?: throw IOException("open failed, url connection null")
            val responseCode = urlConnection.responseCode
            if (responseCode != 200) {
                val errorIoStream = InputStreamReader(urlConnection.errorStream)
                val errorBufferReader = BufferedReader(errorIoStream)
                val errorStringBuilder = StringBuilder()
                var line = errorBufferReader.readLine()
                while (line != null) {
                    errorStringBuilder.append(line)
                    line = errorBufferReader.readLine()
                }
                throw IOException("Response failed, $errorStringBuilder")
            }
            val responseIOStreamReader = InputStreamReader(urlConnection.inputStream)
            val responseBufferReader = BufferedReader(responseIOStreamReader)
            val responseStringBuilder = StringBuilder()
            var line = responseBufferReader.readLine()
            while (line != null) {
                responseStringBuilder.append(line)
                line = responseBufferReader.readLine()
            }
            val responseString = responseStringBuilder.toString()
            //todo how to create JSON -> data class without GSON??
            // or create a json data source parser hub/map
            val response = serializer.fromJson<V>(responseString, V::class.java)
            urlConnection.disconnect()
            return@build response
        }
    }

}
