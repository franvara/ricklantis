package com.franvara.ricklantis.domain.use_cases;

import com.franvara.ricklantis.data.repository.DataProvider;
import com.franvara.ricklantis.domain.DataSource;
import com.franvara.ricklantis.domain.entities.ConnectionState;
import com.franvara.ricklantis.domain.threading.UseCase;

public class SetConnectionStateUseCase extends
        UseCase<SetConnectionStateUseCase.RequestValues, SetConnectionStateUseCase.ResponseValue> {

    private final DataProvider dataProvider;

    public SetConnectionStateUseCase(DataSource dataSource) {
        this.dataProvider = (DataProvider)dataSource;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        dataProvider.setConnectionState(requestValues.getConnectionState());
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final ConnectionState connectionState;

        public RequestValues(ConnectionState connectionState) {
            this.connectionState = connectionState;
        }

        ConnectionState getConnectionState() {
            return this.connectionState;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {}
}
