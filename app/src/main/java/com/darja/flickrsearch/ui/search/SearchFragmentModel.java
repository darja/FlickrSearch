package com.darja.flickrsearch.ui.search;

import com.darja.flickrsearch.model.Photo;

import java.util.List;

class SearchFragmentModel {
    private List<Photo> mPhotos;
    private int mPage = 0;
    private String mQuery = "";

    String getQuery() {
        return mQuery;
    }

    int getPage() {
        return mPage;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photo> photos) {
        mPhotos = photos;
    }

    public void setQuery(String query) {
        mQuery = query;
        mPage = 0;
    }

    boolean isQueryChanged(String query) {
        return mQuery == null ||!mQuery.equalsIgnoreCase(query);
    }
}
