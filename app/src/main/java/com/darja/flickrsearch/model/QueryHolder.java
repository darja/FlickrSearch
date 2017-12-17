package com.darja.flickrsearch.model;

public class QueryHolder {
    private String mQuery;
    private int mPage;

    public QueryHolder(String query) {
        mQuery = query;
        mPage = 0;
    }

    public void setQuery(String query) {
        mQuery = query;
        mPage = 0;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public String getQuery() {
        return mQuery;
    }

    public int getPage() {
        return mPage;
    }
}
