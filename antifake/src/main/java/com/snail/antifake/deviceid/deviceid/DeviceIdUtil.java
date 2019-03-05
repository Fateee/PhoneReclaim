package com.snail.antifake.deviceid.deviceid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.yc.phonerecycle.utils.PermissionUtils;
import org.jetbrains.annotations.NotNull;

/**
 * deviceid的获取
 */

public class DeviceIdUtil {

    public static String getDeviceId(Context context) {

        String deviceId;

        //先看底下
        if (!TextUtils.isEmpty(deviceId = ITelephonyUtil.getDeviceIdLevel2(context))
                || !TextUtils.isEmpty(deviceId = IPhoneSubInfoUtil.getDeviceIdLevel2(context))) {
            return deviceId;
        }
        //再看中部
        if (!TextUtils.isEmpty(deviceId = ITelephonyUtil.getDeviceIdLevel1(context))
                || !TextUtils.isEmpty(deviceId = IPhoneSubInfoUtil.getDeviceIdLevel1(context))) {
            return deviceId;
        }
        //再看上部
        if (!TextUtils.isEmpty(deviceId = IPhoneSubInfoUtil.getDeviceIdLevel0(context))
                || !TextUtils.isEmpty(deviceId = ITelephonyUtil.getDeviceIdLevel0(context))) {
            return deviceId;
        }

        TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        return telephonyManager.getDeviceId();
    }



    //  模拟器存在权限吗？
    public static boolean isEmulatorFromDeviceId(Context context) {
        return isAllZero(getDeviceId(context));
    }

    private static boolean isAllZero(@NonNull String content) {
        if (TextUtils.isEmpty(content))
            return false;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }
}
