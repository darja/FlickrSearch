package com.darja.flickrsearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.darja.flickrsearch.R
import com.darja.flickrsearch.model.Photo

class SearchResultsAdapter(var items: List<Photo>?) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = if (convertView == null)
                LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_search_result, parent, false) as ImageView
            else
                convertView as ImageView
        Glide.with(view).load(items!![position].url).into(view)

        return view
    }

    override fun getItem(position: Int) = items!![position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items?.size ?: 0
}