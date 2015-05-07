package com.whitelaning.whiteframe.tool;

import android.util.Log;

import com.whitelaning.whiteframe.control.DebugCollector;

/**
 * @ClassName: LogUtil
 * @Description: (Log 通用工具类)
 */
public class LogTool {
    private static int VERBOSE = 1;
    private static int DEBUG = 2;
    private static int INFO = 3;
    private static int WARN = 4;
    private static int ERROR = 5;
    private static int NOTHING = 6;
    private static int LEVEL;

    public LogTool() {
        if (DebugCollector.ActivateDebugMode) {
            LEVEL = VERBOSE;// 控制打印的等级，1打印全部，6不打印
        } else {
            LEVEL = NOTHING;
        }
    }

    public static void v(String TAG, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (LEVEL <= INFO) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (LEVEL <= WARN) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(TAG, msg);
        }
    }
}
