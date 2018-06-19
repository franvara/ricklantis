package com.franvara.ricklantis.data.repository.remote;

import com.franvara.ricklantis.domain.entities.BaseError;
import com.franvara.ricklantis.domain.entities.DataCallback;
import com.franvara.ricklantis.domain.use_cases.requests.GetCharactersRequest;
import com.franvara.ricklantis.domain.use_cases.responses.GetCharactersResponse;

public interface IRemoteDataSource {

    void getCharacters(GetCharactersRequest request,
                       DataCallback<GetCharactersResponse, BaseError> callback);

}
