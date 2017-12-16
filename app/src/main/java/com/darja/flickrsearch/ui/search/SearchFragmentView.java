package com.darja.flickrsearch.ui.search;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.darja.flickrsearch.R;
import com.darja.flickrsearch.model.Photo;

import java.util.List;

class SearchFragmentView {
    private EditText query;
    private GridView grid;
    private ProgressBar progress;

    private SearchResultsAdapter gridAdapter = new SearchResultsAdapter();

    Callbacks callbacks;

    SearchFragmentView(View view) {
        query = view.findViewById(R.id.query);
        grid = view.findViewById(R.id.results);
        progress = view.findViewById(R.id.progress);

        grid.setAdapter(gridAdapter);

        setupEvents();
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    void setupEvents() {
        query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (callbacks != null) {
                        callbacks.onNewSearch();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    void showResults(List<Photo> items) {
        if (items != null) {
            gridAdapter.setItems(items);
        }
    }

    void updateResults(List<Photo> items) {
        if (items != null) {
            gridAdapter.setItems(items);
        }
    }

    void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    String getQueryString() {
        return query.getText().toString().trim();
    }

    interface Callbacks {
        void onNewSearch();
    }


}
