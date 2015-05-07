package com.whitelaning.whiteframe.application;

import android.app.Application;

import com.whitelaning.whiteframe.tool.CrashHandlerTool;

public class WhiteFrame extends Application {

    private static WhiteFrame instance;//全局上下文实例

    public synchronized static WhiteFrame getInstance() {
        if (instance == null) {
            instance = new WhiteFrame();
        }
        return instance;
    }

    public synchronized static WhiteFrame getContext() {
        return getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化Crash信息的收集方法
        CrashHandlerTool crashHandler = CrashHandlerTool.getInstance();
        crashHandler.init(this);
    }
}
