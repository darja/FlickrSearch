package com.darja.flickrsearch

import android.app.Activity
import android.os.Bundle
import com.darja.flickrsearch.api.Api
import com.darja.flickrsearch.util.DPLog

class MainActivity : Activity() {
    private val api = Api("2ef2a1173356ea3cb1eeebd1c46ed4d0", "bdf698d55aa42678b0ab117e89bdbe2a")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable {
            val photos = api.requestPhotos("kitten", 0)
            photos?.forEach { DPLog.vt(Api.TAG, it.url) }
        }).start()
    }
}
