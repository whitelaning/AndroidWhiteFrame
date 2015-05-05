package com.whitelaning.whiteframe.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.whitelaning.whiteframe.R;

import java.util.Timer;
import java.util.TimerTask;

public class ToastView<T> {
    private Toast toast; //Toast实例
    private int time;//duration显示时间，临时变量
    private Timer timer;

    public ToastView(Context context, T text) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(text.toString());

        if (toast != null) {
            toast.cancel();
        }

        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
    }

    /**
     * 设置Toast显示位置
     *
     * @param gravity Gravity.CENTER / Gravity.TOP / Gravity.BOTTOM / Gravity.LEFT / Gravity.RIGHT
     * @param xOffset X偏移值
     * @param yOffset Y偏移值
     */
    public void setGravity(int gravity, int xOffset, int yOffset) {
        toast.setGravity(gravity, xOffset, yOffset);
    }

    /**
     * 设置toast显示时间(自定义时间)
     *
     * @param duration 最小值为1000，单位毫秒
     */
    public void setTime(int duration) {
        time = duration;
        timer = new Timer();
        //每一秒钟去执行一次run()方法
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (time - 1000 >= 0) {
                    show();
                    time = time - 1000; //减少1秒
                } else {
                    timer.cancel(); //取消定时器
                }
            }
        }, 0, 1000);
    }

    /**
     * 显示Toast
     */
    public void show() {
        if (null != toast) {
            toast.show();
        }
    }

    /**
     * 取消Toast
     */
    public void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
