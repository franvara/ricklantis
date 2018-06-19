package com.franvara.ricklantis.domain;

import com.franvara.ricklantis.domain.entities.BaseError;
import com.franvara.ricklantis.domain.entities.ConnectionState;
import com.franvara.ricklantis.domain.entities.DataCallback;
import com.franvara.ricklantis.domain.use_cases.requests.GetCharactersRequest;
import com.franvara.ricklantis.domain.use_cases.responses.GetCharactersResponse;

public interface DataSource {

    void setConnectionState(ConnectionState connectionState);

    void getCharacters(GetCharactersRequest request,
                       DataCallback<GetCharactersResponse, BaseError> callback);

}
