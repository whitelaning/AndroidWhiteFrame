package com.whitelaning.activity;

import android.app.Activity;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.View;

import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.view.DialogView;

public class TestActivity extends Activity {
    private DialogView dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.registerParameter("name", UrlQuerySanitizer.getSpaceLegal());
        sanitizer.parseUrl("http://example.com/?name=Zack White&age=25");

        String name = sanitizer.getValue("name");
        String age = sanitizer.getValue("age");

        dialogView = new DialogView(this, "name = " + name + "\nage = " + age);
        dialogView.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });
        dialogView.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });
        dialogView.show();
    }
}
