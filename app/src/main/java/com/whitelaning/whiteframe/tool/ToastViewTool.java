package com.whitelaning.whiteframe.tool;

import android.view.Gravity;

import com.whitelaning.whiteframe.application.WhiteFrame;
import com.whitelaning.whiteframe.view.ToastView;

public class ToastViewTool {
    /**
     * 显示自定义的Toast信息
     *
     * @param info 信息内容
     */
    public static void show(String info) {
        toastShow(new ToastView<java.io.Serializable>(WhiteFrame.getContext(), info));
    }

    public static void show(int info) {
        toastShow(new ToastView<java.io.Serializable>(WhiteFrame.getContext(), info));
    }

    public static void show(Long info) {
        toastShow(new ToastView<java.io.Serializable>(WhiteFrame.getContext(), info));
    }

    public static void show(double info) {
        toastShow(new ToastView<java.io.Serializable>(WhiteFrame.getContext(), info));
    }

    public static void show(float info) {
        toastShow(new ToastView<java.io.Serializable>(WhiteFrame.getContext(), info));
    }

    private static void toastShow(ToastView<java.io.Serializable> toast) {
        toast.setGravity(Gravity.CENTER, 0, 0);//设置居中
        toast.show();
    }
}
