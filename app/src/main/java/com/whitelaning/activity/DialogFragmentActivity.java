package com.whitelaning.activity;

import android.os.Bundle;
import android.view.View;

import com.whitelaning.fragment.LoginDialogFragment;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;
import com.whitelaning.whiteframe.tool.ToastViewTool;

public class DialogFragmentActivity extends BaseActivity implements LoginDialogFragment.LoginInputListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDialogFragment dialog = new LoginDialogFragment();
                dialog.show(getFragmentManager(), "LoginDialogFragment");
            }
        });
    }

    @Override
    public void onLoginInputComplete(String username, String password) {
        ToastViewTool.show("帐号:" + username + "\n密码:" + password);
    }
}
