package com.whitelaning.whiteframe.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.whitelaning.whiteframe.bean.Bean;
import com.whitelaning.whiteframe.tool.LogTool;
import com.whitelaning.whiteframe.util.WhiteViewHolder;

import java.util.List;

//声明属性为protected是为了子类可以访问
public abstract class WhiteAdapter<T> extends BaseAdapter {

    public static final String TAG = "WhiteAdapter";
    protected List<T> list;//数据列表
    protected Context context;//上下文
    protected LayoutInflater layoutInflater;//布局填充器
    protected int layoutId;//布局Id
    protected Handler handler;

    /**
     * WhiteAdapter的构造函数，在这里初始化各项属性值
     *
     * @param context  上下文
     * @param layoutId 布局Id
     * @param list     数据列表
     */
    public WhiteAdapter(Context context, int layoutId, List<T> list) {
        this.layoutId = layoutId;
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);//获取布局填充器实例
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WhiteViewHolder whiteViewHolder = WhiteViewHolder.getInstance(context, convertView, parent, layoutId, position);
        initialize(whiteViewHolder, getItem(position), position);
        return whiteViewHolder.getConvertView();
    }

    public abstract void initialize(WhiteViewHolder holder, T item, int position);

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}