package com.hy.eatinghelper.quickopen.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtils {
    public static boolean isConnectWifi(Context paramContext) {
        int i = 1;
        NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if ((localNetworkInfo != null) && (localNetworkInfo.getType() == i)) {
            return true;
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context paramContext) {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if ((localNetworkInfo != null) && (localNetworkInfo.isConnected())) {
            return true;
        }
        return false;
    }
}