package com.darja.flickrsearch

import android.app.Activity
import android.os.Bundle
import com.darja.flickrsearch.api.Api
import com.darja.flickrsearch.util.DPLog

class MainActivity : Activity() {
    private lateinit var api: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api = Api(getString(R.string.api_key))

        Thread(Runnable {
            val photos = api.requestPhotos("kitten", 0)
            photos?.forEach { DPLog.vt(Api.TAG, it.url) }
        }).start()
    }
}
