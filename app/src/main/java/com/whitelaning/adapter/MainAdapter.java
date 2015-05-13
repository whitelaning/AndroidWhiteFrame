package com.whitelaning.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.whitelaning.bean.ClassBean;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.adapter.WhiteAdapter;
import com.whitelaning.whiteframe.tool.LogTool;
import com.whitelaning.whiteframe.util.WhiteViewHolder;

import java.util.List;

public class MainAdapter extends WhiteAdapter {
    public static final String TAG = "MainAdapter";
    public MainAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        LogTool.i(TAG, "datas = " + datas.toString());
    }

    @Override
    public void initialize(WhiteViewHolder holder, Object item, int position) {
        final ClassBean bean = (ClassBean) item;
        Button button = holder.getView(R.id.button);
        button.setText(bean.getTitle());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Toast(context).makeText(context,bean.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, bean.getmClass());
                context.startActivity(intent);
            }
        });
    }
}
