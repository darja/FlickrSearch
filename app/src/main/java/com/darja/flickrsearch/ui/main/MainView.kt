package com.darja.flickrsearch.ui.main

import android.app.Activity
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.GridView
import com.darja.flickrsearch.R
import com.darja.flickrsearch.model.Photo
import com.darja.flickrsearch.ui.adapter.SearchResultsAdapter

class MainView(activity: Activity) {
    val query = activity.findViewById<EditText>(R.id.query)
    val grid = activity.findViewById<GridView>(R.id.results)

    var callbacks: Callbacks? = null

    init {
        query.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callbacks?.onNewSearch()
                true
            } else {
                false
            }
        }
    }

    fun showResults(items: List<Photo>?) {
        if (items != null) {
            grid.adapter = SearchResultsAdapter(items)
        }
    }

    fun getQueryString(): String {
        return query.text.toString().trim()
    }

    interface Callbacks {
        fun onNewSearch()
    }
}