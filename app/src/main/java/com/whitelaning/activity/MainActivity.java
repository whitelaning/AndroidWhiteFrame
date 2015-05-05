package com.whitelaning.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.whitelaning.adapter.MainAdapter;
import com.whitelaning.bean.ClassBean;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<ClassBean> list = new ArrayList<ClassBean>();
    private ListView listView;
    private MainAdapter mainAdapter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        list.add(new ClassBean("（列表视图）List View", ListViewActivity.class));
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        mainAdapter = new MainAdapter(this, R.layout.adapter_main, list);
        listView.setAdapter(mainAdapter);
    }
}
