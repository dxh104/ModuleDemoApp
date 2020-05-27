package com.administer.example.common_base.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by XHD on 2019/03/29
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> views;
    private final Context context;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.views = new SparseArray<View>();
    }

    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(int id, CharSequence value) {
        TextView view = getView(id);
        view.setText(value);
        return this;
    }

    public BaseViewHolder setImageUrl(int id, String imageUrl) {
        ImageView view = getView(id);
        Glide.with(context).asDrawable().load(imageUrl).into(view);
        return this;
    }

    public BaseViewHolder setVisible(int id, boolean visible) {
        View view = getView(id);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseViewHolder linkify(int id) {
        TextView view = getView(id);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

}