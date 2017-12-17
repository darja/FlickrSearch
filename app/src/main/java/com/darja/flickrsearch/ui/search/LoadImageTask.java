package com.darja.flickrsearch.ui.search;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.ImageView;
import com.darja.flickrsearch.util.DPLog;
import com.darja.flickrsearch.util.ImageUtil;
import com.darja.flickrsearch.util.ImagesCache;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

class LoadImageTask extends AsyncTask<Void, Void, BitmapDrawable> {
    private final WeakReference<ImageView> mImageRef;
    private final WeakReference<ImagesCache> mImagesCacheRef;
    private final String mUrl;

    LoadImageTask(ImageView mHolderRef, ImagesCache imagesCache, String url) {
        mImageRef = new WeakReference<>(mHolderRef);
        mImagesCacheRef = new WeakReference<>(imagesCache);
        mUrl = url;
    }

    @Override
    protected BitmapDrawable doInBackground(Void... params) {
        ImagesCache cache = mImagesCacheRef.get();
        if (cache != null) {
            BitmapDrawable cached = cache.get(mUrl);
            if (cached != null) {
                DPLog.d("Image is cached: [%s]", mUrl);
                return cached;
            }
        }

        try {
            URLConnection conn = new URL(mUrl).openConnection();
            conn.connect();
            Bitmap bitmap = ImageUtil.decodeBitmap(conn.getInputStream(), 200, 200);
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            drawable.setGravity(Gravity.FILL);
            if (cache != null) {
                cache.put(mUrl, drawable);
            }
            return drawable;

        } catch (IOException e) {
            DPLog.e(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(BitmapDrawable result) {
        super.onPostExecute(result);
        ImageView imageView = mImageRef.get();

        if (imageView != null && imageView.getTag().equals(mUrl)) {
            imageView.setImageDrawable(result);
        }

        ImagesCache cache = mImagesCacheRef.get();
        if (cache != null) {
            cache.put(mUrl, result);
        }
    }
}
