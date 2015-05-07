package com.whitelaning.whiteframe.view;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.whitelaning.whiteframe.R;

public class DialogView {
    public Dialog mDialog;
    public TextView dialog_title;
    public TextView dialog_message;
    public TextView positive;
    public TextView negative;

    public DialogView(Context context, String title, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        dialog_message = (TextView) view.findViewById(R.id.dialog_message);
        dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        dialog_message.setText(message);
        dialog_title.setText(title);
        positive = (TextView) view.findViewById(R.id.yes);
        negative = (TextView) view.findViewById(R.id.no);
    }

    public DialogView(Context context, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        dialog_message = (TextView) view.findViewById(R.id.dialog_message);
        dialog_message.setText(message);
        positive = (TextView) view.findViewById(R.id.yes);
        negative = (TextView) view.findViewById(R.id.no);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }
}
