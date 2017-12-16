package com.darja.flickrsearch.ui.search;

import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.darja.flickrsearch.R;
import com.darja.flickrsearch.model.Photo;
import com.darja.flickrsearch.util.TrimInputFilter;

import java.util.List;

class SearchFragmentView {
    private EditText mQuery;
    private GridView mGrid;
    private ProgressBar mProgress;

    private SearchResultsAdapter mGridAdapter = new SearchResultsAdapter();

    private Callbacks mCallbacks;

    SearchFragmentView(View view) {
        mQuery = view.findViewById(R.id.query);
        mGrid = view.findViewById(R.id.results);
        mProgress = view.findViewById(R.id.progress);

        mGrid.setAdapter(mGridAdapter);

        setupEvents();
    }

    void setCallbacks(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    private void setupEvents() {
        mQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mCallbacks != null) {
                        mCallbacks.onNewSearch();
                    }
                    return true;
                }
                return false;
            }
        });

        mQuery.setFilters(new InputFilter[]{
            new TrimInputFilter()
        });

        mGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemsCount, int totalItemsCount) {
                if (mCallbacks != null) {
                    mCallbacks.onScrolled(firstVisibleItem + visibleItemsCount,
                        totalItemsCount);
                }
            }
        });
    }

    void showResults(List<Photo> items) {
        if (items != null) {
            mGridAdapter.setPhotos(items);
        }
    }

    void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    String getQueryString() {
        return mQuery.getText().toString().trim();
    }

    interface Callbacks {
        void onNewSearch();
        void onScrolled(int lastVisibleItem, int totalItemsCount);
    }
}
