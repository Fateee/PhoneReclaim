package com.yc.phonerecycle.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import com.yc.phonerecycle.app.BaseApplication;

public class NetUtil {
    /**
     * 监测网络是否可用
     *
     * @return
     */
    public static boolean checkNetworkState() {
        Context context = BaseApplication.getAppContext();
        if (context == null) return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                Network[] networks = connectivityManager.getAllNetworks();
                if (networks == null) return false;
                for (Network network : networks) {
                    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                    if (networkInfo != null && networkInfo.isConnected())
                        return true;
                }
            } else {
                NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
                if (networkInfos == null) return false;
                for (NetworkInfo networkInfo : networkInfos) {
                    if (networkInfo != null && networkInfo.isConnected())
                        return true;
                }
            }
        }
        return false;
    }
}
