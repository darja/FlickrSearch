package com.darja.flickrsearch

import android.app.Activity
import android.os.Bundle
import com.darja.flickrsearch.api.Api
import com.darja.flickrsearch.ui.main.MainModel
import com.darja.flickrsearch.ui.main.MainView
import com.darja.flickrsearch.util.DPLog

class MainActivity : Activity(), MainView.Callbacks {
    private lateinit var api: Api

    private lateinit var view: MainView
    private lateinit var model: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api = Api(getString(R.string.api_key))
        view = MainView(this)
        view.callbacks = this

        model = MainModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        view.callbacks = null
    }

    private fun loadPhotos() {
        Thread(Runnable {
            val photos = api.requestPhotos(model.query, model.page)
            photos?.forEach { DPLog.vt(Api.TAG, it.url) }
            model.photos = photos

            runOnUiThread({ view.showResults(photos) })
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
