package com.franvara.ricklantis.data.repository.memory;

import com.franvara.ricklantis.domain.entities.ConnectionState;

public class MemoryDataSource implements IMemoryDataSource {

    //region Fields

    private ConnectionState connectionState = ConnectionState.NOT_CONNECTED;

    //endregion

    //region Constructor

    public MemoryDataSource() {

    }

    //endregion

    //region IMemoryDataSource implementation

    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }

    //endregion
}
