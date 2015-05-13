package com.whitelaning.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;

import com.whitelaning.adapter.MultithreadingBreakpointContinuinglyAdapter;
import com.whitelaning.bean.FileInfoBean;
import com.whitelaning.services.DownloadService;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;
import com.whitelaning.whiteframe.tool.ToastViewTool;

import java.util.ArrayList;
import java.util.List;

public class MultithreadingBreakpointContinuinglyActivity extends BaseActivity {

    public static final String TAG = "MultithreadingBreakpointContinuinglyActivity";

    private ListView listView;

    private List<FileInfoBean> fileList = new ArrayList();
    private MultithreadingBreakpointContinuinglyAdapter multithreadingBreakpointContinuinglyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multithreading_breakpoint_continuingly);
        initTestData();
        initView();
        initData();
        initRegister();
    }

    private void initRegister() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        filter.addAction(DownloadService.ACTION_FINISH);
        filter.addAction(DownloadService.ACTION_STOP);
        registerReceiver(broadcastReceiver, filter);
    }

    private void initTestData() {
        FileInfoBean fileInfo1 = new FileInfoBean(1, "http://192.168.1.21:8080/startFromScratch1.txt", "startFromScratch1.txt", 0, 0);
        FileInfoBean fileInfo2 = new FileInfoBean(2, "http://192.168.1.21:8080/startFromScratch2.txt", "startFromScratch2.txt", 0, 0);
        FileInfoBean fileInfo3 = new FileInfoBean(3, "http://192.168.1.21:8080/startFromScratch3.txt", "startFromScratch3.txt", 0, 0);
        FileInfoBean fileInfo4 = new FileInfoBean(4, "http://192.168.1.21:8080/startFromScratch4.txt", "startFromScratch4.txt", 0, 0);
        FileInfoBean fileInfo5 = new FileInfoBean(5, "http://192.168.1.21:8080/startFromScratch5.txt", "startFromScratch5.txt", 0, 0);
        FileInfoBean fileInfo6 = new FileInfoBean(6, "http://192.168.1.21:8080/startFromScratch6.txt", "startFromScratch6.txt", 0, 0);
        fileList.add(fileInfo1);
        fileList.add(fileInfo2);
        fileList.add(fileInfo3);
        fileList.add(fileInfo4);
        fileList.add(fileInfo5);
        fileList.add(fileInfo6);
    }

    private void initData() {
        multithreadingBreakpointContinuinglyAdapter = new MultithreadingBreakpointContinuinglyAdapter(this, R.layout.adapter_multithreading_breakpoing_continuingly, fileList);
        listView.setAdapter(multithreadingBreakpointContinuinglyAdapter);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(DownloadService.ACTION_UPDATE)) {
                int finished = intent.getIntExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                int length = intent.getIntExtra("length", 0);
                int changelength = intent.getIntExtra("changelength", 0);

                for (FileInfoBean fileInfo : fileList)
                    if (fileInfo.getId() == id) {
                        fileInfo.setLength(length);
                        fileInfo.setFinished(fileInfo.getFinished() + changelength);
                        multithreadingBreakpointContinuinglyAdapter.notifyDataSetChanged();
                    }
            } else if (action.equals(DownloadService.ACTION_FINISH)) {
                int id = intent.getIntExtra("id", -1);
                for (FileInfoBean fileInfo : fileList) {
                    if (fileInfo.getId() == id) {
                        ToastViewTool.show(fileInfo.getName() + "download finished!");
                        fileInfo.setFinished(fileInfo.getLength());
                        multithreadingBreakpointContinuinglyAdapter.notifyDataSetChanged();
                    }
                }
            } else if (action.equals(DownloadService.ACTION_STOP)) {
                FileInfoBean fileInfoBean = (FileInfoBean) intent.getSerializableExtra(FileInfoBean.TAG);
                int id = fileInfoBean.getId();
                for (FileInfoBean fileInfo : fileList) {
                    if (fileInfo.getId() == id) {
                        fileInfo.setFinished(0);
                        multithreadingBreakpointContinuinglyAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
