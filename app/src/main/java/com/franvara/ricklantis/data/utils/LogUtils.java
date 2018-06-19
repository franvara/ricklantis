package com.franvara.ricklantis.data.utils;

import android.util.Log;

import com.franvara.ricklantis.BuildConfig;


public class LogUtils {

    public static void LogError(String title, String description) {
        if (BuildConfig.LOG_ENABLED) {
            Log.e(title, description);
        }
    }

    public static void LogDebug(String title, Throwable throwable) {
        LogDebug(title, throwable.toString());
    }

    public static void LogDebug(String title, String description) {
        if (BuildConfig.LOG_ENABLED) {
            Log.d(title, description);
        }
    }
}
