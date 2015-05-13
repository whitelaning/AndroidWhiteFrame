package com.whitelaning.whiteframe.control;

import android.os.Environment;

public class PathAddressCollector {
    public static final String LOG_PATH = Environment.getExternalStorageDirectory()+ "/WhiteFrame/log/";//log文件地址
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory()+ "/WhiteFrame/download/";//文件下载地址
}
