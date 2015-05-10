package com.whitelaning.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import com.whitelaning.bean.PersonBean;
import com.whitelaning.whiteframe.R;
import com.whitelaning.whiteframe.activity.BaseActivity;

public class DataTransferBetween1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transfer_between1);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonBean person = new PersonBean("Angelle Yu", "25", "女", "1990-07-28");
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

                Bundle bundle = new Bundle();
                bundle.putSerializable("person", person);
                bundle.putParcelable("bitmap", bitmap);

                Intent intent = new Intent();
                intent.setClass(DataTransferBetween1Activity.this, DataTransferBetween2Activity.class);

                intent.putExtra("person", person);
//                intent.putExtras(bundle);

//                intent.putExtra("bitmap", bitmap);
                intent.putExtras(bundle); //这里需要注意，当传递的数据大小超过512KB的话，程序就会崩溃....

                startActivity(intent);
            }
        });
    }
}
