package com.whitelaning.whiteframe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.control.ActivityCollector;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);// Activity初始化时，加入AcitivityCollector的List中
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);// Activity被销毁时，在AcitivityCollector的List中移除
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_translate_right_in, R.anim.activity_translate_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_translate_right_in, R.anim.activity_translate_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_translate_right_close_in, R.anim.activity_translate_right_close_out);
    }
}
