package com.whitelaning.whiteframe.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WhiteViewHolder {
    private SparseArray<View> viewList;
    private int position;
    private View convertView;
    private Context context;
    private int layoutId;

    private WhiteViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.context = context;
        this.layoutId = layoutId;
        this.position = position;

        this.viewList = new SparseArray<View>();

        convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        convertView.setTag(this);
    }

    public static WhiteViewHolder getInstance(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (null == convertView) {
            return new WhiteViewHolder(context, parent, layoutId, position);
        } else {
            WhiteViewHolder whiteViewHolder = (WhiteViewHolder) convertView.getTag();
            whiteViewHolder.position = position;
            return whiteViewHolder;
        }
    }

    public <T extends View> T getView(int ViewId) {
        View view = viewList.get(ViewId);
        if (null == view) {
            view = convertView.findViewById(ViewId);
            viewList.put(ViewId, view);
        }

        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    public WhiteViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public WhiteViewHolder setImageResource(int viewId, int imageId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(imageId);
        return this;
    }

    public WhiteViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public WhiteViewHolder setBackgroundColor(int viewId, int color) {
        ImageView imageView = getView(viewId);
        imageView.setBackgroundColor(color);
        return this;
    }

    public WhiteViewHolder setBackgroundColor(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setBackground(drawable);
        return this;
    }
}