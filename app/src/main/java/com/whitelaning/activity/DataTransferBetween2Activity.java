package com.whitelaning.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.whitelaning.bean.PersonBean;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;

public class DataTransferBetween2Activity extends BaseActivity {
    private static final String TAG = "Test2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transfer_between2);

        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent != null) {
            PersonBean person = (PersonBean) intent.getSerializableExtra("person");
            Bitmap bitmap = intent.getParcelableExtra("bitmap");

            if(person != null) {
                textView.setText(person.getName() + "\n" + person.getSex() + "\n" + person.getAge());
            } else {
                textView.setText("person == null");
            }

            if(bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Log.i(TAG, "bitmap == null");
                imageView.setBackgroundColor(getResources().getColor(R.color.blue));
            }

        } else {
            textView.setText("getIntent() == null");
            imageView.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }
}
