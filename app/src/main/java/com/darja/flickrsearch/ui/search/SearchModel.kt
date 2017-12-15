package com.darja.flickrsearch.ui.search

import com.darja.flickrsearch.model.Photo

internal class SearchModel {
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