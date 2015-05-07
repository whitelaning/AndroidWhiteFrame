package com.whitelaning.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.whitelaning.adapter.WhiteAdapterDemoAdapter;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;
import com.whitelaning.whiteframe.bean.Bean;
import com.whitelaning.whiteframe.tool.ToastViewTool;

import java.util.ArrayList;
import java.util.Date;

public class WhiteAdapterDemoActivity extends BaseActivity {
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
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = datas.remove(position).getTitle();
                ToastViewTool.show(title);
                whiteAdapterDemoAdapter.setList(datas);
            }
        });
    }

    private void testData() {
        String time = new Date() + "";
        for (int i = 1; i < 20; i++) {
            datas.add(new Bean("Title - " + i, "Content - " + i, time));
        }
    }
}
