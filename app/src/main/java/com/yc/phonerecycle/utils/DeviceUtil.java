package com.yc.phonerecycle.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.*;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.WindowManager;
import com.yc.phonerecycle.app.BaseApplication;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.BATTERY_SERVICE;

/**
 * Created by jmyf on 17/5/23.
 */

public final class DeviceUtil {
    public static final String TAG = DeviceUtil.class.getSimpleName();
    public static boolean isMeiZu = false;
    public static boolean isXiaomi = false;
    public static boolean isChuizi = false;
    public static boolean isNexus = false;
    public static boolean isHuawei = false;
    public static boolean isMeitu = false;
    public static boolean isSamsung = false;
    public static boolean isHuaweiG6C00 = false;

    public static boolean isMX3 = false;
    public static boolean isMX2 = false;
    public static boolean isMi2A = false;
    public static boolean isHwP8 = false;
    public static boolean isHwP7 = false;
    public static boolean isX86 = false;
    public static boolean isSumsungS7 = false;

    public static boolean isMeituOSv2 = false;
    public static boolean isMeituOSv3 = false;
    public static boolean isHw_CHMTL00 = false;

    public static boolean isHw_h60_L03 = false;
    public static boolean isHw_PE_TL20 = false;
    public static boolean isCoolpad = false;
    public static boolean isZTE = false;
    public static boolean isViVo = false;
    public static boolean isSony = false;
    public static  String sIMEI = "";

    public static  float density = 3.0f;

    static {
         /*检测厂商*/
        String model = Build.MODEL.trim().toLowerCase();
        if (Build.MANUFACTURER.trim().toLowerCase().equals("xiaomi")) {
            isXiaomi = true;
        }
        if (Build.MANUFACTURER.trim().toLowerCase().equals("meizu")) {
            isMeiZu = true;
        }
        if (Build.MODEL.trim().toLowerCase().contains("nexus")) {
            isNexus = true;
        }

        if (Build.MODEL.trim().toLowerCase().contains("zte")) {
            isZTE = true;
        }

        if (Build.MODEL.trim().toLowerCase().contains("vivo")) {
            isViVo = true;
        }

        if (Build.MODEL.trim().toLowerCase().contains("sony")) {
            isSony = true;
        }

        if (Build.MODEL.trim().toLowerCase().contains("g6-c00")) {
            isHuaweiG6C00 = true;
        }


        if (Build.MANUFACTURER.trim().toLowerCase().contains("huawei")) {
            isHuawei = true;
        }
        if (model.contains("coolpad")) {
            isCoolpad = true;
        }
        if (Build.MANUFACTURER.trim().toLowerCase().contains("samsung")) {
            isSamsung = true;
        }

        /*检测机型*/
        if (Build.MANUFACTURER.trim().toLowerCase().contains("meitu")) {
            isMeitu = true;
            String osVer = readVersion("ro.build.version.meios");
            if (!TextUtils.isEmpty(osVer)) {
                osVer = osVer.trim();
                if (osVer.startsWith("2")) {
                    isMeituOSv2 = true;
                } else if (osVer.startsWith("3")) {
                    isMeituOSv3 = true;
                }
            }
        }
        if (model.equalsIgnoreCase("m353") || Build.DEVICE.equalsIgnoreCase("mx3")) {
            isMX3 = true;
        } else if (model.equalsIgnoreCase("m040") || Build.DEVICE.equalsIgnoreCase("mx2")) {
            isMX2 = true;
        } else if (model.contains("2a")) {
            isMi2A = true;
        } else if (model.equalsIgnoreCase("ale-tl00") || Build.DEVICE.equalsIgnoreCase("hwALE-H")) {
            isHwP8 = true;
        } else if (model.equalsIgnoreCase("sm701") || Build.DEVICE.equalsIgnoreCase("msm8974sfo")) {
            isChuizi = true;
        } else if (model.equalsIgnoreCase("HUAWEI P7-L09")) {
            isHwP7 = true;
        } else if (model.equalsIgnoreCase("SM-G9350")) {
            isSumsungS7 = true;
        } else if (model.equalsIgnoreCase("CHM-TL00")) {
            isHw_CHMTL00 = true;
        } else if (model.equalsIgnoreCase("H60-L03")) {
            isHw_h60_L03 = true;
        } else if (model.equalsIgnoreCase("PE-TL20")) {
            isHw_PE_TL20 = true;
        }

        /*检测平台 x86 x64*/
        if (Build.CPU_ABI != null && Build.CPU_ABI.toLowerCase().contains("x86")) {
            isX86 = true;
        }
    }

    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    //这个是获取手机系统版本号的
    public static String readVersion(String buildVersion) {
        String version = "";
        try {
            Class mClass = Class.forName("android.os.SystemProperties");
            Method getMethod = mClass.getDeclaredMethod("get", String.class);
            getMethod.setAccessible(true);
            version = (String) getMethod.invoke(null, buildVersion);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return version;
    }

    public static boolean isMainProcess(Context c) {

        if(c == null)
            return false;

        ActivityManager am = (ActivityManager) c.getSystemService(Context
                .ACTIVITY_SERVICE);
        List processes = am.getRunningAppProcesses();
        String packageName = c.getPackageName();
        int myPid = Process.myPid();
        if (processes != null) {
            Iterator iterator = processes.iterator();

            ActivityManager.RunningAppProcessInfo processInfo;
            do {
                if (!iterator.hasNext()) {
                    return false;
                }
                processInfo = (ActivityManager.RunningAppProcessInfo) iterator.next();
            }
            while (processInfo.pid != myPid || !TextUtils.equals(packageName, processInfo
                    .processName));
        }
        return true;
    }

    public static String getAvailRamSize() {
        ActivityManager manager = (ActivityManager) BaseApplication.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        return Formatter.formatFileSize(BaseApplication.getAppContext(), info.availMem);
    }

    public static String getTotalRamSize() {
        ActivityManager manager = (ActivityManager) BaseApplication.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        return Formatter.formatFileSize(BaseApplication.getAppContext(), info.totalMem);
    }

    public static String getAvailRomSize() {
        final StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long availableCounts = statFs.getAvailableBlocksLong() ; //获取可用的block数
        long size = statFs.getBlockSizeLong(); //每格所占的大小，一般是4KB==
        long availROMSize = availableCounts * size;//可用内部存储大小

        return Formatter.formatFileSize(BaseApplication.getAppContext(), availROMSize);
    }

    public static String getTotalRomSize() {
        final StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long totalCounts = statFs.getBlockCountLong();//总共的block数
        long size = statFs.getBlockSizeLong(); //每格所占的大小，一般是4KB==
        long totalROMSize = totalCounts *size; //内部存储总大小

        return Formatter.formatFileSize(BaseApplication.getAppContext(), totalROMSize);
    }

    //得到SD卡大小
    public static String getSdcardSize(){

        File path = Environment.getExternalStorageDirectory();//得到SD卡的路径
        StatFs stat = new StatFs(path.getPath());//创建StatFs对象，用来获取文件系统的状态
        long blockCount = stat.getBlockCount();
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();

        String totalSize = Formatter.formatFileSize(BaseApplication.getAppContext(), blockCount*blockSize);//格式化获得SD卡总容量
        String availableSize = Formatter.formatFileSize(BaseApplication.getAppContext(), availableBlocks*blockSize);//获得SD卡可用容

        return availableSize+"/"+totalSize;
    }

    public static boolean isWifiAvailable() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean ret = false;
        if (mWiFiNetworkInfo != null) {
            ret = mWiFiNetworkInfo.isAvailable();
        }
        if (!ret) {
            WifiManager wifiManager = (WifiManager) BaseApplication.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            ret = wifiManager.isWifiEnabled();
            if (!ret) {
                ret = wifiManager.setWifiEnabled(true);
            }
        }
        return ret;
    }

    public static boolean isWifiConect() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isConnected();
        }
        return false;
    }

    public static boolean isBleAvailable() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mBleInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
        if (mBleInfo != null) {
            return mBleInfo.isAvailable();
        }
        return false;
    }

    public static boolean isBleConect() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mBleInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
        if (mBleInfo != null) {
            return mBleInfo.isConnected();
        }
        return false;
    }



    /**
     * Unknown network class
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;

    /**
     * wifi net work
     */
    public static final int NETWORK_WIFI = 1;

    /**
     * "2G" networks
     */
    public static final int NETWORK_CLASS_2_G = 2;

    /**
     * "3G" networks
     */
    public static final int NETWORK_CLASS_3_G = 3;

    /**
     * "4G" networks
     */
    public static final int NETWORK_CLASS_4_G = 4;
    public static int getNetWorkClass() {
        TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;

            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;

            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    public static int getNetWorkStatus() {
        int netWorkType = NETWORK_CLASS_UNKNOWN;

        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass();
            }
        }
        return netWorkType;
    }

    public static String getNetWorkString() {
        int netWorkType = getNetWorkStatus();
        switch (netWorkType) {
            case NETWORK_CLASS_2_G:
                return "2G";
            case NETWORK_CLASS_3_G:
                return "3G";
            case NETWORK_CLASS_4_G:
                return "4G";
            case NETWORK_WIFI:
                return "WIFI";
            default:
                return "未知";
        }
    }


    public static int getBatteryLevel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager batteryManager = (BatteryManager) BaseApplication.getAppContext().getSystemService(BATTERY_SERVICE);
            return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {
            Intent intent = new ContextWrapper(BaseApplication.getAppContext()).
                    registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            return (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100) /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }
    }

    public static void setIsFullScreen(Activity activity, boolean isFullScreen) {
        if (isFullScreen) {
            //设置为全屏
            activity.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            //设置为非全屏
            final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(attrs);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

}
