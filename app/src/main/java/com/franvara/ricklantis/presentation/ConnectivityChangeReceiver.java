package com.franvara.ricklantis.presentation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.franvara.ricklantis.domain.entities.ConnectionState;
import com.franvara.ricklantis.domain.threading.UseCaseHandler;
import com.franvara.ricklantis.domain.use_cases.SetConnectionStateUseCase;
import com.franvara.ricklantis.domain.use_cases.SetConnectionStateUseCase.RequestValues;
import com.franvara.ricklantis.presentation.base.IConnectionChangeListener;

import static com.franvara.ricklantis.domain.entities.ConnectionState.CONNECTED;
import static com.franvara.ricklantis.domain.entities.ConnectionState.NOT_CONNECTED;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    private static final String KEY_EXTRA_NETWORK_INFO = "networkInfo";

    private static ConnectivityChangeReceiver INSTANCE;
    private final UseCaseHandler useCaseHandler;
    private final SetConnectionStateUseCase setConnectionStateUseCase;
    private IConnectionChangeListener connectivityChangeListener;
    private ConnectionState connectionState = NOT_CONNECTED;

    private ConnectivityChangeReceiver(UseCaseHandler useCaseHandler,
                                       SetConnectionStateUseCase setConnectionStateUseCase) {
        this.useCaseHandler = useCaseHandler;
        this.setConnectionStateUseCase = setConnectionStateUseCase;
    }

    public static ConnectivityChangeReceiver getInstance(
            UseCaseHandler useCaseHandler, SetConnectionStateUseCase setConnectionStateUseCase) {
        if (INSTANCE == null) {
            INSTANCE = new ConnectivityChangeReceiver(useCaseHandler, setConnectionStateUseCase);
        }
        return INSTANCE;
    }

    public void setConnectivityChangeListener(IConnectionChangeListener listener) {
        this.connectivityChangeListener = listener;
        toogleSnackbar();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            NetworkInfo networkInfo = (NetworkInfo) extras.get(KEY_EXTRA_NETWORK_INFO);
            if (connectivityChangeListener != null && networkInfo != null) {
                ConnectionState previousConnectionState = connectionState;
                connectionState = networkInfo.isConnected() ? CONNECTED : NOT_CONNECTED;
                persistConnectionState();
                toogleSnackbar();
                refreshActualView(previousConnectionState);
            }
        }
    }

    private void persistConnectionState() {
        useCaseHandler.execute(setConnectionStateUseCase, new RequestValues(connectionState),
                null);
    }

    private void refreshActualView(ConnectionState previousConnectionState) {
        if (previousConnectionState == NOT_CONNECTED && connectionState == CONNECTED) {
            connectivityChangeListener.onConnectionRestored();
        }
    }

    private void toogleSnackbar() {
        connectivityChangeListener.toogleConnectionSnackbar(connectionState);
    }
}
