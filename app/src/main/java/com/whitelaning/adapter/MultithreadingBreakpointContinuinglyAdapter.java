package com.whitelaning.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whitelaning.bean.FileInfoBean;
import com.whitelaning.services.DownloadService;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.adapter.WhiteAdapter;
import com.whitelaning.whiteframe.tool.LogTool;
import com.whitelaning.whiteframe.util.WhiteViewHolder;

import java.util.List;

public class MultithreadingBreakpointContinuinglyAdapter extends WhiteAdapter {

    public static final String TAG = "MultithreadingBreakpointContinuinglyAdapter";
    /**
     * WhiteAdapter的构造函数，在这里初始化各项属性值
     *
     * @param context  上下文
     * @param layoutId 布局Id
     * @param list     数据列表
     */
    public MultithreadingBreakpointContinuinglyAdapter(Context context, int layoutId, List list) {
        super(context, layoutId, list);
    }

    @Override
    public void initialize(WhiteViewHolder holder, Object item, int position) {
        TextView fileName = holder.getView(R.id.fileName);
        TextView progressTextView = holder.getView(R.id.progressTextView);
        ProgressBar progressBar = holder.getView(R.id.progressBar);
        Button startButton = holder.getView(R.id.startButton);
        Button stopButton = holder.getView(R.id.stopButton);

        final FileInfoBean fileInfo = (FileInfoBean) item;

        fileName.setText(fileInfo.getName());
        progressBar.setMax(100);

        float progress = 0;
        if(fileInfo.getLength() != 0) {
            progress = (float)fileInfo.getFinished() / (float)fileInfo.getLength();
            LogTool.i(TAG,"fileInfo.getFinished() = " + fileInfo.getFinished());
            LogTool.i(TAG,"fileInfo.getLength() = " + fileInfo.getLength());
            LogTool.i(TAG,"progress = " + progress);
            LogTool.i(TAG,"(int)(progress * 100) = " + (int)(progress * 100));
        }

        progressBar.setProgress((int)(progress * 100));
        progressTextView.setText((int)(progress * 100) + "%");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent downloadServiceStartIntent = new Intent(context, DownloadService.class);
                downloadServiceStartIntent.setAction(DownloadService.ACTION_START);
                downloadServiceStartIntent.putExtra(FileInfoBean.TAG, fileInfo);
                context.startService(downloadServiceStartIntent);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent downloadServiceStopIntent = new Intent(context, DownloadService.class);
                downloadServiceStopIntent.setAction(DownloadService.ACTION_STOP);
                downloadServiceStopIntent.putExtra(FileInfoBean.TAG, fileInfo);
                context.startService(downloadServiceStopIntent);
            }
        });
    }
}
