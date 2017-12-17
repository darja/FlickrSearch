package com.darja.flickrsearch.model;

import com.darja.flickrsearch.util.DPLog;
import org.json.JSONException;
import org.json.JSONObject;

public class Photo {
    private String mId;
    private String mSecret;
    private String mServer;
    private String mFarm;

    public Photo(JSONObject src) {
        try {
            mId = src.getString("id");
            mSecret = src.getString("secret");
            mServer = src.getString("server");
            mFarm = src.getString("farm");

        } catch (JSONException e) {
            DPLog.e(e);
        }
    }

    public String getImageUrl() {
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s_z.jpg",
            mFarm, mServer, mId, mSecret);
    }
}
