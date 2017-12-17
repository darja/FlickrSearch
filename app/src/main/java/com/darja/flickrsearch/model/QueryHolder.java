package com.darja.flickrsearch.model;

public class QueryHolder {
    private String mQuery;
    private int mPage;

    public QueryHolder(String query) {
        this(query, 1);
    }

    public QueryHolder(String query, int page) {
        mQuery = query;
        mPage = page;
    }

    public void setQuery(String query) {
        mQuery = query;
        mPage = 1;
    }

    public String getQuery() {
        return mQuery;
    }

    public int getPage() {
        return mPage;
    }

    public void incPage() {
        mPage++;
    }
}
