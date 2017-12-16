package com.darja.flickrsearch.api;

import com.darja.flickrsearch.model.Photo;
import com.darja.flickrsearch.util.DPLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class FlickrParser {
    static List<Photo> parseSearchResults(String response) {
        try {
            JSONObject root = new JSONObject(response);
            JSONArray photos = root.getJSONObject("photos")
                .getJSONArray("photo");

            List<Photo> result = new ArrayList<>();
            for (int i = 0; i < photos.length(); ++i) {
                result.add(new Photo(photos.getJSONObject(i)));
            }
            return result;

        } catch (Exception e) {
            DPLog.e(e);
            return null;
        }
    }
}
