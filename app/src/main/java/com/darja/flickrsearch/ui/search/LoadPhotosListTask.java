package com.darja.flickrsearch.ui.search;

import android.os.AsyncTask;
import com.darja.flickrsearch.api.FlickrApi;
import com.darja.flickrsearch.model.PhotosPage;
import com.darja.flickrsearch.model.QueryHolder;

import java.lang.ref.WeakReference;

public class LoadPhotosListTask extends AsyncTask<Void, Void, PhotosPage> {
    private final FlickrApi mApi;
    private final WeakReference<OnPhotosLoadedCallback> mCallback;
    private final QueryHolder mQuery;

    LoadPhotosListTask(FlickrApi api, OnPhotosLoadedCallback callback, QueryHolder query) {
        mApi = api;
        mCallback = new WeakReference<>(callback);
        mQuery = query;
    }

    @Override
    protected PhotosPage doInBackground(Void... params) {
        return mApi.requestPhotos(mQuery.getQuery(), mQuery.getPage());
    }

    @Override
    protected void onPostExecute(PhotosPage result) {
        super.onPostExecute(result);
        OnPhotosLoadedCallback callback = mCallback.get();
        if (callback != null) {
            callback.onPhotosLoaded(mQuery, result);
        }
    }

    interface OnPhotosLoadedCallback {
        void onPhotosLoaded(QueryHolder queryHolder, PhotosPage photosPage);
    }
}
