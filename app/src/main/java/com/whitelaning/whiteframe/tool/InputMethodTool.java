package com.whitelaning.whiteframe.tool;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class InputMethodTool {
    public final static void closeInputMethod(Activity activity) {
        try {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
