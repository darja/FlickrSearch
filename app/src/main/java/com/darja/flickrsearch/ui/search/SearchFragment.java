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
    private FlickrApi mApi;

    private SearchFragmentView mView;
    private SearchFragmentModel mModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Photo> photos = mApi.requestPhotos(mModel.getQuery(), mModel.getPage());
                for (Photo p : photos) {
                    DPLog.vt(FlickrApi.TAG, p.getImageUrl());

                }
                mModel.setPhotos(photos);

                if (isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isAdded()) {
                                mView.showResults(photos);
                                mView.hideProgress();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onNewSearch() {
        String query = mView.getQueryString();
        if (mModel.isQueryChanged(query)) {
            mModel.setQuery(query);
            loadPhotos();
        }
    }
}
