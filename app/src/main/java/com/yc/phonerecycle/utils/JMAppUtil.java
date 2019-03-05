package com.yc.phonerecycle.utils;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.yc.phonerecycle.app.BaseApplication;

import java.util.List;

/**
 * Created by xiangc on 2017/4/14.
 */

public class JMAppUtil {

    public static boolean isActivityExist(Intent intent){
        List<ResolveInfo> list = BaseApplication.getApplication().getPackageManager().queryIntentActivities(intent, 0);
        if (list.size() == 0) {
            return false;
        }
        return true;
    }

    private static long lastClickTime;
    private static final long MAX_MULTI_CLICK_INTERVAL = 1000;

    public static boolean isFastMultiClick() {
        long time = System.currentTimeMillis();
        long detal = time - lastClickTime;
        if (0 < detal && detal < MAX_MULTI_CLICK_INTERVAL) {
            return true;
        } else {
            lastClickTime = time;
        }
        return false;
    }
}
