package com.whitelaning.activity;

import android.app.Activity;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.widget.TextView;

import com.whitelaning.whiteframe.R;

/**
 * 解析Url地址的参数
 */
public class UrlQuerySanitizerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_query_sanitizer);

        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();//获取实例
        sanitizer.setAllowUnregisteredParamaters(true);//把所有的参数都获取
        sanitizer.registerParameter("name", UrlQuerySanitizer.getSpaceLegal());//保留name这个key的Value的空格
        String urlString = "http://example.com/?name=Zack White&age=25";
        sanitizer.parseUrl(urlString);//测试用例

        String name = sanitizer.getValue("name");
        String age = sanitizer.getValue("age");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Url = " + urlString + "\nname = " + name + "\nage = " + age);
    }
}
