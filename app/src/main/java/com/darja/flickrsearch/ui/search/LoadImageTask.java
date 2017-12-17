package com.darja.flickrsearch.ui.search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.ImageView;
import com.darja.flickrsearch.util.DPLog;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

class LoadImageTask extends AsyncTask<String, Void, Pair<String, Bitmap>> {
    private final WeakReference<ImageView> mImageRef;

    LoadImageTask(ImageView mHolderRef) {
        this.mImageRef = new WeakReference<>(mHolderRef);
    }

    @Override
    protected Pair<String, Bitmap> doInBackground(String... params) {
        try {
            String url = params[0];
            URLConnection conn = new URL(params[0]).openConnection();
            conn.connect();
            // todo decode options
            Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            return new Pair<>(url, bitmap);
        } catch (IOException e) {
            DPLog.e(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Pair<String, Bitmap> result) {
        super.onPostExecute(result);
        ImageView imageView = mImageRef.get();
        if (imageView != null && imageView.getTag().equals(result.first)) {
            imageView.setImageBitmap(result.second);
        }
    }
}
