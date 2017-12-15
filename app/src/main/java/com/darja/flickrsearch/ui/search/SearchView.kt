package com.darja.flickrsearch.ui.search

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.GridView
import android.widget.ProgressBar
import com.darja.flickrsearch.R
import com.darja.flickrsearch.model.Photo
import com.darja.flickrsearch.ui.adapter.SearchResultsAdapter

class SearchView(view: View) {
    private val query = view.findViewById<EditText>(R.id.query)
    private val grid = view.findViewById<GridView>(R.id.results)
    private val progress = view.findViewById<ProgressBar>(R.id.progress)

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

    fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progress.visibility = View.GONE
    }

    fun getQueryString(): String {
        return query.text.toString().trim()
    }

    interface Callbacks {
        fun onNewSearch()
    }
}