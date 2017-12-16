package com.darja.flickrsearch.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.darja.flickrsearch.R;
import com.darja.flickrsearch.model.Photo;

import java.util.List;

public class SearchResultsAdapter extends BaseAdapter {
    List<Photo> items;

    public void setItems(List<Photo> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
        Glide.with(view).load(items.get(position).getImageUrl()).into(view);

        return view;
    }
}
