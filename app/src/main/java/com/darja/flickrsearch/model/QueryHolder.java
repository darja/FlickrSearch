package com.darja.flickrsearch.model;

public class QueryHolder {
    private String mQuery;
    private int mPage;

    public QueryHolder(String query, int page) {
        mQuery = query;
        mPage = page;
    }

    public String getQuery() {
        return mQuery;
    }

    public int getPage() {
        return mPage;
    }
}
