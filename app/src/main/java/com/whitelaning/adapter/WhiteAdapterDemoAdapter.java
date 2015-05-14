package com.whitelaning.adapter;

import android.content.Context;
import android.widget.TextView;

import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.adapter.WhiteAdapter;
import com.whitelaning.whiteframe.bean.Bean;
import com.whitelaning.whiteframe.tool.LogTool;
import com.whitelaning.whiteframe.util.WhiteViewHolder;

import java.util.List;

public class WhiteAdapterDemoAdapter extends WhiteAdapter {

    public static final String TAG = "WhiteAdapter";

    public WhiteAdapterDemoAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void initialize(WhiteViewHolder holder, Object item, int position) {
        Bean bean = (Bean) item;
        holder.setText(R.id.title, bean.getTitle());
        holder.setText(R.id.content, bean.getContent());
        holder.setText(R.id.time, bean.getTime());
    }
}
