package com.franvara.ricklantis.presentation.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.franvara.ricklantis.data.utils.LogUtils;

public class GenericUtils {

    public static boolean isNetworkConnectionAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;

        } catch (Exception e) {

            LogUtils.LogDebug("Check Connection Error", e.toString());
        }
        return false;
    }


}
