package com.whitelaning.test;


import android.os.Bundle;

import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;

public class TestActivity extends BaseActivity {
    private static final String TAG = TestActivity.class.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initListener();
    }

    private void initListener() {
    }

    private void initView() {
    }
}
