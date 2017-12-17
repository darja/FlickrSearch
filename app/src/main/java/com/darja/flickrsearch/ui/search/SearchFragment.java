package com.darja.flickrsearch.ui.search;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.darja.flickrsearch.R;
import com.darja.flickrsearch.api.FlickrApi;
import com.darja.flickrsearch.model.PhotosPage;
import com.darja.flickrsearch.model.QueryHolder;
import com.darja.flickrsearch.util.DPLog;

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
            mView = new SearchFragmentView(getActivity().getApplicationContext(), rootView);
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
        DPLog.checkpoint();

        mView.showProgress();
        mModel.setLoading(true);

        LoadPhotosListTask loadPhotosListTask = new LoadPhotosListTask(mApi, this,
            new QueryHolder(mModel.getQuery(), mModel.getPage()));
        loadPhotosListTask.execute();
    }

    private void loadMorePhotos() {
        DPLog.checkpoint();

        mModel.setLoading(true);

        LoadPhotosListTask loadPhotosListTask = new LoadPhotosListTask(mApi, this,
            new QueryHolder(mModel.getQuery(), mModel.getPage() + 1));
        loadPhotosListTask.execute();
    }

    @Override
    public void onNewSearch() {
        String query = mView.getQueryString();
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(getActivity(), R.string.error_empty_query, Toast.LENGTH_SHORT).show();

        } else if (mModel.isQueryChanged(query) || mModel.getPhotos() == null || mModel.getPhotos().isEmpty()) {
            mView.hideKeyboard(getActivity());
            mModel.setQuery(query);
            loadPhotos();
        }
    }

    @Override
    public void onScrolled(int lastVisibleItem, int totalItemsCount) {
        if (lastVisibleItem > totalItemsCount - 15) {
            if (!mModel.isLoading() && mModel.canLoadMore()) {
                DPLog.i("Scrolled to [%s] of [%s], should load more", lastVisibleItem, totalItemsCount);
                loadMorePhotos();
            }
        }
    }

    @Override
    public void onPhotosLoaded(QueryHolder query, PhotosPage searchResult) {
        if (isAdded()) {
            if (!mModel.isLastQuery(query.getQuery())) {
                return;
            }

            int emptyResId = 0;
            if (searchResult == null) {
                if (query.getPage() <= 1) {
                    mView.showResults(null, R.string.error_cannot_reach_server);
                } else {
                    Toast.makeText(getActivity(), R.string.error_cannot_reach_server, Toast.LENGTH_SHORT).show();
                }
                return;
            }

            if (searchResult.getPage() > 1) {
                mModel.appendPhotos(searchResult.getPhotos());
            } else {
                if (searchResult.getPhotos().size() == 0) {
                    emptyResId = R.string.nothing_found;
                }
                mModel.setPhotos(searchResult.getPhotos());
            }

            mModel.setPage(query.getPage());
            mModel.setTotalPages(searchResult.getTotalPages());
            mModel.setLoading(false);

            mView.showResults(mModel.getPhotos(), emptyResId);
            if (query.getPage() == 1) {
                mView.scrollResultsToBeginning();
            }

            mView.hideProgress();
        }
    }
}
