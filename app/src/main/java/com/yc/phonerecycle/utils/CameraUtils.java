package com.yc.phonerecycle.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import com.yc.phonerecycle.app.BaseApplication;

public class CameraUtils {

    public static final int CAMERA_FACING_BACK = 0;
    public static final int CAMERA_FACING_FRONT = 1;
    public static final int CAMERA_NONE = 2;

    public static int HasBackCamera()
    {
        int numberOfCameras = Camera.getNumberOfCameras();
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CAMERA_FACING_BACK) {
                return i;
            }
        }
        return 2;
    }

    public static int HasFrontCamera()
    {
        int numberOfCameras = Camera.getNumberOfCameras();
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CAMERA_FACING_FRONT) {
                return i;
            }
        }
        return 2;
    }

    public static String getCameraPixels(int paramInt)
    {
        if (paramInt == 2)
            return "无";
        Camera localCamera = Camera.open(paramInt);
        Camera.Parameters localParameters = localCamera.getParameters();
        localParameters.set("camera-id", 1);
        List<Size> localList = localParameters.getSupportedPictureSizes();
        if (localList != null)
        {
            int heights[] = new int[localList.size()];
            int widths[] = new int[localList.size()];
            for (int i = 0; i < localList.size(); i++)
            {
                Size size = (Size) localList.get(i);
                int sizehieght = size.height;
                int sizewidth = size.width;
                heights[i] = sizehieght;
                widths[i] =sizewidth;
            }
            int pixels = getMaxNumber(heights) * getMaxNumber(widths);
            localCamera.release();
            return String.valueOf(pixels / 10000) + " 万";

        }
        else return "无";

    }

    public static int getMaxNumber(int[] paramArray)
    {
        int temp = paramArray[0];
        for(int i = 0;i<paramArray.length;i++)
        {
            if(temp < paramArray[i])
            {
                temp = paramArray[i];
            }
        }
        return temp;
    }

    /**
     * 获取CPU型号
     * @return
     */
    public static String getCpuName(){

        String str1 = "/proc/cpuinfo";
        String str2 = "";

        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2=localBufferedReader.readLine()) != null) {
                if (str2.contains("Hardware")) {
                    return str2.split(":")[1];
                }
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * @ 获取当前手机屏幕的尺寸(单位:像素)
     */
    public static float getPingMuSize(Context mContext) {
        int densityDpi = mContext.getResources().getDisplayMetrics().densityDpi;
        float scaledDensity = mContext.getResources().getDisplayMetrics().scaledDensity;
        float density = mContext.getResources().getDisplayMetrics().density;
        float xdpi = mContext.getResources().getDisplayMetrics().xdpi;
        float ydpi = mContext.getResources().getDisplayMetrics().ydpi;
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int height = mContext.getResources().getDisplayMetrics().heightPixels;

        // 这样可以计算屏幕的物理尺寸
        float width2 = (width / xdpi)*(width / xdpi);
        float height2 = (height / ydpi)*(width / xdpi);

        return (float) Math.sqrt(width2+height2);
    }

    /**
     * 判断设备是否支持多点触控
     * @param context
     * @return
     */
    public static boolean isSupportMultiTouch(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean isSupportMultiTouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
        return isSupportMultiTouch;
    }

    public static boolean isSupportGravity(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean hasGSensor = pm.hasSystemFeature(PackageManager.FEATURE_SCREEN_PORTRAIT)
                && pm.hasSystemFeature(PackageManager.FEATURE_SCREEN_LANDSCAPE);
        return hasGSensor;
    }

    public static boolean isSupportCompass(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean hasCompass = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);
        return hasCompass;
    }

    public static boolean isSupportSensorLight(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean hasLightSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT);
        return hasLightSensor;
    }

    public static boolean isSupportDistance() {
        PackageManager pm = BaseApplication.getAppContext().getPackageManager();
        boolean hasProxSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY);
        return hasProxSensor;
    }

    public static boolean isSupportGyroscope() {
        PackageManager pm = BaseApplication.getAppContext().getPackageManager();
        boolean hasGyroSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE);
        return hasGyroSensor;
    }

    public static boolean isSupportAccelerometer() {
        PackageManager pm = BaseApplication.getAppContext().getPackageManager();
        boolean hasGyroSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        return hasGyroSensor;
    }
}

