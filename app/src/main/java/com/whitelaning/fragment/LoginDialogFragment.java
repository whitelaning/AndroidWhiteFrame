package com.whitelaning.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.whitelaning.whiteframe.R;

public class LoginDialogFragment extends DialogFragment {
    private EditText username;
    private EditText password;

    public interface LoginInputListener {
        void onLoginInputComplete(String username, String password);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//获取AlertDialog.Builder的实例

        LayoutInflater inflater = getActivity().getLayoutInflater();//获取布局填充器
        View view = inflater.inflate(R.layout.fragment_login_dialog, null);//导入布局文件

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);

        builder.setView(view);//设置对话框布局
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Sign in", new LoginDialogFragmentOnClickListener());

        return builder.create();
    }

    private class LoginDialogFragmentOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            LoginInputListener listener = (LoginInputListener) getActivity();
            listener.onLoginInputComplete(username.getText().toString(), password.getText().toString());
        }
    }
}
