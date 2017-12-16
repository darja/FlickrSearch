package com.darja.flickrsearch.ui.search;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.darja.flickrsearch.R;
import com.darja.flickrsearch.api.FlickrApi;
import com.darja.flickrsearch.model.Photo;
import com.darja.flickrsearch.util.DPLog;

import java.util.List;

public class SearchFragment extends Fragment implements SearchFragmentView.Callbacks {
    private FlickrApi api;

    private SearchFragmentView view;
    private SearchFragmentModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        setRetainInstance(true);

        api = new FlickrApi(getString(R.string.api_key));

        if (rootView != null) {
            view = new SearchFragmentView(rootView);
            view.callbacks = this;
        }

        model = new SearchFragmentModel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view.callbacks = null;
    }

    private void loadPhotos() {
        view.showProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Photo> photos = api.requestPhotos(model.getQuery(), model.getPage());
                for (Photo p : photos) {
                    DPLog.vt(FlickrApi.TAG, p.getImageUrl());

                }
                model.setPhotos(photos);

                if (isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isAdded()) {
                                view.showResults(photos);
                                view.hideProgress();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onNewSearch() {
        String query = view.getQueryString();
        if (model.isQueryChanged(query)) {
            model.setQuery(query);
            loadPhotos();
        }
    }
}
