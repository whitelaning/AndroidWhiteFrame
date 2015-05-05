package com.whitelaning.whiteframe.control;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    /**
     * 将开打的Activity添加进入activities
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 将开打的Activity从activities中移除
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 结束所有还没用被系统收回的Activity
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    /**
     * 结束所有还没用被系统收回的Activity,保留mActivity
     * @param mActivity 不结束的Activity
     */
    public static void finishAll(Activity mActivity) {
        for (Activity activity : activities) {
            if (!activity.equals(mActivity)) {
                activity.finish();
            }
        }
    }
}
