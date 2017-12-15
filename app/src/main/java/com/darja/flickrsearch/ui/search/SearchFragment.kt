package com.darja.flickrsearch.ui.search

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darja.flickrsearch.R
import com.darja.flickrsearch.api.Api
import com.darja.flickrsearch.util.DPLog

class SearchFragment : Fragment(), SearchView.Callbacks {
    private lateinit var api: Api

    private lateinit var view: SearchView
    private lateinit var model: SearchModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(rootView: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)
        retainInstance = true

        api = Api(getString(R.string.api_key))

        if (rootView != null) {
            view = SearchView(rootView)
            view.callbacks = this
        }

        model = SearchModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        view.callbacks = null
    }

    private fun loadPhotos() {
        view.showProgress()
        Thread(Runnable {
            val photos = api.requestPhotos(model.query, model.page)
            photos?.forEach { DPLog.vt(Api.TAG, it.url) }
            model.photos = photos

            activity?.runOnUiThread({
                if (isAdded) {
                    view.showResults(photos)
                    view.hideProgress()
                }
            })
        }).start()
    }

    override fun onNewSearch() {
        val query = view.getQueryString()
        if (model.isQueryChanged(query)) {
            model.query = query
            loadPhotos()
        }
    }

}