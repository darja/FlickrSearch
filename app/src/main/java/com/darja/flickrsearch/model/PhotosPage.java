package com.darja.flickrsearch.model;

import com.darja.flickrsearch.util.DPLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhotosPage {
    private int mPage;
    private int mTotalPages;
    private List<Photo> mPhotos;

    public PhotosPage(JSONObject src) {
        try {
            mPage = src.getInt("page");
            mTotalPages = src.getInt("pages");

            JSONArray photos = src.getJSONArray("photo");
            mPhotos = new ArrayList<>();
            for (int i = 0; i < photos.length(); ++i) {
                try {
                    mPhotos.add(new Photo(photos.getJSONObject(i)));
                } catch (JSONException ignored) {}
            }

        } catch (JSONException e) {
            DPLog.e(e);
        }
    }

    public int getPage() {
        return mPage;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }
}
