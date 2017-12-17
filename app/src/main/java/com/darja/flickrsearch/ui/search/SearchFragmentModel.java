package com.darja.flickrsearch.ui.search;

import com.darja.flickrsearch.model.Photo;

import java.util.List;

class SearchFragmentModel {
    private List<Photo> mPhotos;
    private int mPage = 1;
    private String mQuery = "";
    private boolean mIsLoading = false;

    String getQuery() {
        return mQuery;
    }

    int getPage() {
        return mPage;
    }

    void setPage(int page) {
        mPage = page;
    }

    List<Photo> getPhotos() {
        return mPhotos;
    }

    void setPhotos(List<Photo> photos) {
        mPhotos = photos;
    }

    void appendPhotos(List<Photo> photos) {
        mPhotos.addAll(photos);
    }

    void setQuery(String query) {
        mQuery = query;
        mPage = 0;
    }

    boolean isQueryChanged(String query) {
        return mQuery == null ||!mQuery.equalsIgnoreCase(query);
    }

    boolean isLoading() {
        return mIsLoading;
    }

    void setLoading(boolean loading) {
        mIsLoading = loading;
    }

    boolean isLastQuery(String query) {
        return mQuery.equalsIgnoreCase(query);
    }
}
