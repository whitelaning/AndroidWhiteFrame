package com.whitelaning.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.whitelaning.adapter.WhiteAdapterDemoAdapter;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;
import com.whitelaning.whiteframe.bean.Bean;
import com.whitelaning.whiteframe.tool.LogTool;
import com.whitelaning.whiteframe.tool.ToastViewTool;

import java.util.ArrayList;
import java.util.Date;

public class WhiteAdapterDemoActivity extends BaseActivity {

    public static final String TAG = "WhiteAdapterDemoActivity";

    private ListView listView;
    private ArrayList<Bean> datas = new ArrayList<Bean>();
    private WhiteAdapterDemoAdapter whiteAdapterDemoAdapter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, WhiteAdapterDemoActivity.class);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_adapter_demo);
        testData();//设置测试数据
        initView();//初始化实例
        initData();//初始化显示数据
        initListener();//初始化事件
    }

    private void initData() {
        listView.setAdapter(whiteAdapterDemoAdapter);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        whiteAdapterDemoAdapter = new WhiteAdapterDemoAdapter(this, R.layout.adapter_white_adapter_demo, datas);
        whiteAdapterDemoAdapter.setHandler(WhiteAdapterDemoHandler);
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bean bean = datas.get(position);
                bean.setTitle("测试 + " + position);
                whiteAdapterDemoAdapter.notifyDataSetChanged();
            }
        });
    }

    private void testData() {
        String time = new Date() + "";
        for (int i = 0; i < 20; i++) {
            datas.add(new Bean("Title - " + i, "Content - " + i, time));
        }
    }

    private Handler WhiteAdapterDemoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}
