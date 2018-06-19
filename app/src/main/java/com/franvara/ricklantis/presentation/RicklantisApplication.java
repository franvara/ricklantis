package com.franvara.ricklantis.presentation;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.franvara.ricklantis.Injector;

public class RicklantisApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerReceiver(Injector.provideConnectivityChangeReceiver(getApplicationContext()),
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }
}
