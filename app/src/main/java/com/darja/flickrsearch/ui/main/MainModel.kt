package com.darja.flickrsearch.ui.main

import com.darja.flickrsearch.model.Photo

class MainModel {
    var photos: List<Photo>? = null
    var page: Int = 0
    private var queryString: String = ""

    var query: String
        get() = queryString
        set(value) {
            queryString = value
            page = 0
        }

    fun isQueryChanged(query: String): Boolean {
        return queryString != query
    }
}