package com.darja.flickrsearch.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.darja.flickrsearch.R;
import com.darja.flickrsearch.model.Photo;
import com.darja.flickrsearch.util.ImagesCache;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchResultsAdapter extends BaseAdapter {
    private List<Photo> mPhotos;
    private ImagesCache mImagesCache;
    private ExecutorService mExecutor = Executors.newFixedThreadPool(5);

    SearchResultsAdapter(Context context) {
        mImagesCache = new ImagesCache(context);
    }

    void setPhotos(List<Photo> photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPhotos == null ? 0 : mPhotos.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_result, parent, false);
        }
        String url = mPhotos.get(position).getImageUrl();
        if (!url.equals(view.getTag())) {
            view.setImageBitmap(null);
            view.setTag(url);
            new LoadImageTask(view, mImagesCache, url).executeOnExecutor(mExecutor);
        }

        return view;
    }
}
