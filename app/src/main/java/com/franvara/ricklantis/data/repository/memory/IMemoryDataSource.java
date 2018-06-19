package com.franvara.ricklantis.data.repository.memory;

import com.franvara.ricklantis.domain.entities.ConnectionState;

public interface IMemoryDataSource {

    ConnectionState getConnectionState();

    void setConnectionState(ConnectionState connectionState);

}
