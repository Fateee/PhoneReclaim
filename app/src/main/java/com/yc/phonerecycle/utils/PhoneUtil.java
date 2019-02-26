package com.yc.phonerecycle.utils;

import android.text.TextUtils;

public class PhoneUtil {

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "^((1[3,5,7,8][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    public static String hidePhoneNum(String phone) {
        if (TextUtils.isEmpty(phone)) return "";
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }
}
