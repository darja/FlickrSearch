package com.darja.flickrsearch.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.LruCache;

public class ImagesCache extends LruCache<String, BitmapDrawable> {
    public ImagesCache(Context context) {
        super(getCacheSize(context));
    }

    private static int getCacheSize(Context context) {
        int cut = 15;
        ActivityManager mgr = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        if (mgr != null) {
            cut = mgr.getMemoryClass() / 8;
        }

        return cut * 1024 * 1024;
    }

    @Override
    protected int sizeOf(String key, BitmapDrawable value) {
        Bitmap bm = value.getBitmap();
        if (bm != null) {
            return bm.getByteCount();
        }
        return 0;
    }
}
