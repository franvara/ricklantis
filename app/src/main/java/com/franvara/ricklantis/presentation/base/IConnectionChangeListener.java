package com.franvara.ricklantis.presentation.base;

import com.franvara.ricklantis.domain.entities.ConnectionState;

public interface IConnectionChangeListener {

    void toogleConnectionSnackbar(ConnectionState connectionState);
    void onConnectionRestored();
}
