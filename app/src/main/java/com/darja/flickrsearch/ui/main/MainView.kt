package com.darja.flickrsearch.ui.main

import android.app.Activity
import android.widget.EditText
import android.widget.GridView
import com.darja.flickrsearch.R
import com.darja.flickrsearch.model.Photo
import com.darja.flickrsearch.ui.adapter.SearchResultsAdapter

class MainView(activity: Activity) {
    val query = activity.findViewById<EditText>(R.id.query)
    val grid = activity.findViewById<GridView>(R.id.results)

    fun showResults(items: List<Photo>?) {
        if (items != null) {
            grid.adapter = SearchResultsAdapter(items)
        }
    }
}