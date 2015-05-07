package com.whitelaning.activity;

import android.os.Bundle;

import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;
import com.whitelaning.whiteframe.view.DialogView;

public class TestActivity extends BaseActivity {
    private DialogView dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }
}
