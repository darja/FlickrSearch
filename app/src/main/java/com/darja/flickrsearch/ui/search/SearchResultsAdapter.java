package com.darja.flickrsearch.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.darja.flickrsearch.R;
import com.darja.flickrsearch.model.Photo;

import java.util.List;

public class SearchResultsAdapter extends BaseAdapter {
    private List<Photo> mPhotos;

    void setPhotos(List<Photo> photos) {
        this.mPhotos = photos;
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
        view.setImageBitmap(null);
        view.setTag(url);
        new LoadImageTask(view).execute(url);

        return view;
    }
}