package com.darja.flickrsearch.model

import org.json.JSONObject

class Photo(src: JSONObject) {
    private val id = src.getString("id")
    private val secret = src.getString("secret")
    private val server = src.getString("server")
    private val farm = src.getString("farm")

    val url: String
        get() = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"

}

