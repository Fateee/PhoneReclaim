package com.yc.phonerecycle.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }
}
