package com.darja.flickrsearch.ui.search;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.darja.flickrsearch.R;
import com.darja.flickrsearch.api.FlickrApi;
import com.darja.flickrsearch.model.Photo;
import com.darja.flickrsearch.model.QueryHolder;

import java.util.List;

public class SearchFragment extends Fragment implements
    SearchFragmentView.Callbacks,
    LoadPhotosListTask.OnPhotosLoadedCallback
{

    private FlickrApi mApi;

    private SearchFragmentView mView;
    private SearchFragmentModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        setRetainInstance(true);

        mApi = new FlickrApi(getString(R.string.api_key));

        if (rootView != null) {
            mView = new SearchFragmentView(rootView);
            mView.setCallbacks(this);
        }

        mModel = new SearchFragmentModel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mView.setCallbacks(null);
    }

    private void loadPhotos() {
        mView.showProgress();

        LoadPhotosListTask loadPhotosListTask = new LoadPhotosListTask(mApi, this, new QueryHolder(mModel.getQuery()));
        loadPhotosListTask.execute();
    }

    @Override
    public void onNewSearch() {
        String query = mView.getQueryString();
        if (mModel.isQueryChanged(query)) {
            mModel.setQuery(query);
            loadPhotos();
        }
    }

    @Override
    public void onScrolled(int lastVisibleItem, int totalItemsCount) {
        if (lastVisibleItem > totalItemsCount - 10) {
            // todo load more photos
        }
    }

    @Override
    public void onPhotosLoaded(QueryHolder queryHolder, List<Photo> photos) {
        if (isAdded()) {
            mModel.setPhotos(photos);
            int emptyResId = 0;
            if (photos == null) {
                emptyResId = R.string.error_cannot_reach_server;
            } else if (photos.size() == 0) {
                emptyResId = R.string.nothing_found;
            }

            mView.showResults(photos, emptyResId);
            mView.hideProgress();
        }
    }
}
