package com.whitelaning.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.whitelaning.activity.ListView.WhiteAdapterDemoActivity;
import com.whitelaning.adapter.ListViewAdapter;
import com.whitelaning.bean.ClassBean;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends BaseActivity {
    private List<ClassBean> list = new ArrayList<ClassBean>();
    private ListView listView;
    private ListViewAdapter listViewAdapter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, ListViewActivity.class);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initData();
        initView();
    }

    private void initData() {
        list.add(new ClassBean("封装BaseAdapter的ViewHolder", WhiteAdapterDemoActivity.class));
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        listViewAdapter = new ListViewAdapter(this, R.layout.adapter_list_view, list);
        listView.setAdapter(listViewAdapter);
    }
}
