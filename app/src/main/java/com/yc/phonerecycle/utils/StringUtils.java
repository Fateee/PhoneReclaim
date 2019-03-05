package com.yc.phonerecycle.utils;

import android.text.TextUtils;

public class StringUtils {
    public static String EmptyString = "";

    public static int parseToInt(String si) {
        return parseToInt(si, 0);
    }

    public static int parseToInt(String si, int defaultValue) {

        if (TextUtils.isEmpty(si)) {
            return defaultValue;
        }

        int result = defaultValue;
        try {
            result = Integer.parseInt(si);
        } catch (NumberFormatException ex) {
            result = defaultValue;
        }

        return result;
    }

    public static boolean parseToBoolean(String si, boolean defaultValue) {
        if (isBlank(si)) return defaultValue;
        boolean result = defaultValue;
        try {
            result = Boolean.parseBoolean(si);
        } catch (NumberFormatException ex) {
            result = defaultValue;
        }
        return result;
    }

    public static long parseToLong(String si) {
        return parseToLong(si, 0l);
    }

    public static long parseToLong(String si, long defaultValue) {
        if (TextUtils.isEmpty(si)) {
            return defaultValue;
        }

        long result = defaultValue;
        try {
            result = Long.parseLong(si);
        } catch (NumberFormatException ex) {
            result = defaultValue;
        }

        return result;
    }

    public static float parseToFloat(String si) {
        return parseToFloat(si, 0f);
    }

    public static float parseToFloat(String si, float defaultValue) {
        if (TextUtils.isEmpty(si)) {
            return defaultValue;
        }

        float result = defaultValue;
        try {
            result = Float.parseFloat(si);
        } catch (NumberFormatException ex) {
            result = defaultValue;
        }

        return result;
    }

    public static boolean isBlank(String value) {
        if (TextUtils.isEmpty(value)) return true;
        return TextUtils.isEmpty(value.trim());
    }

    public static String transToStr(String value) {
        return isBlank(value) ? "" : value;
    }
}
