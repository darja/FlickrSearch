package com.darja.flickrsearch.api;

import android.text.TextUtils;
import com.darja.flickrsearch.model.Photo;
import com.darja.flickrsearch.util.DPLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FlickrApi {
    private static final String TAG = DPLog.createTag("API");
    private static final String BASE_URL = "https://api.flickr.com/services/rest/";
    private static final int SEARCH_PAGE_SIZE = 30;

    private final String mApiKey;

    public FlickrApi(String apiKey) {
        mApiKey = apiKey;
    }

    public List<Photo> requestPhotos(String query, int page) {
        String url = String.format("%s?method=flickr.photos.search&api_key=%s&tags=%s&per_page=%s&page=%s&format=json&nojsoncallback=1",
            BASE_URL, mApiKey, query, SEARCH_PAGE_SIZE, page);
        try {
            String response = get(new URL(url));
            if (!TextUtils.isEmpty(response)) {
                return FlickrParser.parseSearchResults(response);
            }
        } catch (IOException e) {
            DPLog.e(e);
            // todo handle request or parsing error
        }

        return null;
    }

    private String get(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        StringBuilder response = new StringBuilder();
        try {
            conn.setDoOutput(true);
            conn.setChunkedStreamingMode(0);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            DPLog.dt(TAG, "GET [%s]: %s [%s]", url, conn.getResponseCode(), conn.getResponseMessage());
            DPLog.dt(TAG, "Response: %s", response);

        } catch (Exception e ) {
            DPLog.et(TAG, e);
        } finally {
            conn.disconnect();
        }
        return response.toString();
    }

}
