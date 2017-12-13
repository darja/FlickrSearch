package com.darja.flickrsearch.api

import com.darja.flickrsearch.model.Photo
import com.darja.flickrsearch.util.DPLog
import org.json.JSONObject
import java.util.*

internal class FlickrParser {
    internal fun parseSearchResults(results: String): List<Photo>? {
        try {
            val root = JSONObject(results)
            val photos = root
                .getJSONObject("photos")
                ?.getJSONArray("photo")
                ?: return null

            return (0 until photos.length())
                .mapTo(LinkedList()) { Photo(photos.getJSONObject(it)) }

        } catch (e: Exception) {
            DPLog.e(e)
            return null
        }
    }
}