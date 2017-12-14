package com.darja.flickrsearch.api

import com.darja.flickrsearch.model.Photo
import com.darja.flickrsearch.util.DPLog
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class Api(val apiKey: String) {
    companion object {
        val TAG = DPLog.createTag("API")
        val BASE_URL = "https://api.flickr.com/services/rest/"
        val SEARCH_PAGE_SIZE = 20
    }

    fun requestPhotos(query: String, page: Int): List<Photo>? {
        val url = "$BASE_URL?method=flickr.photos.search&api_key=$apiKey&tags=$query&per_page=$SEARCH_PAGE_SIZE&page=$page&format=json&nojsoncallback=1"
        val stream = get(URL(url))

        return if (stream.isNotEmpty()) FlickrParser().parseSearchResults(stream) else null
    }

    private fun get(url: URL): String {
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Content-Type", "application/json")

        val response = StringBuilder()
        try {
            conn.doOutput = true
            conn.setChunkedStreamingMode(0)

            val reader = BufferedReader(InputStreamReader(conn.inputStream))
            reader.forEachLine { response.append(it) }

            DPLog.dt(TAG, "GET [%s]: %s [%s]", url, conn.responseCode, conn.responseMessage)
            DPLog.dt(TAG, "Response: %s", response)

        } catch (e: Exception) {
            DPLog.e(TAG)
        } finally {
            conn.disconnect()
        }
        return response.toString()
    }
}