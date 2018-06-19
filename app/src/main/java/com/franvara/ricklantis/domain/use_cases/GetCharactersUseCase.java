package com.franvara.ricklantis.domain.use_cases;

import com.franvara.ricklantis.data.repository.DataProvider;
import com.franvara.ricklantis.data.utils.LogUtils;
import com.franvara.ricklantis.domain.entities.BaseError;
import com.franvara.ricklantis.domain.entities.DataCallback;
import com.franvara.ricklantis.domain.threading.UseCase;
import com.franvara.ricklantis.domain.use_cases.requests.GetCharactersRequest;
import com.franvara.ricklantis.domain.use_cases.responses.GetCharactersResponse;

public class GetCharactersUseCase extends UseCase<GetCharactersUseCase.RequestValues, GetCharactersUseCase.ResponseValue>{

    private final DataProvider dataProvider;

    public GetCharactersUseCase(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        dataProvider.getCharacters(requestValues.getCharactersRequest(),
                new DataCallback<GetCharactersResponse, BaseError>() {
                    @Override
                    public void onSuccess(GetCharactersResponse response) {
                        LogUtils.LogDebug("GetCharactersUC", "onGetCharactersSuccess");
                        getUseCaseCallback().onSuccess(new ResponseValue(response));
                    }

                    @Override
                    public void onFailure(BaseError errorBase) {
                        LogUtils.LogDebug("GetCharactersUC", "onGetCharactersFailure");
                        getUseCaseCallback().onError(errorBase.getErrorMessage());
                    }
                });

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final GetCharactersRequest getCharactersRequest;

        public RequestValues(GetCharactersRequest getCharactersRequest) {
            this.getCharactersRequest = getCharactersRequest;
        }

        public GetCharactersRequest getCharactersRequest() {
            return getCharactersRequest;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final GetCharactersResponse getCharactersResponse;

        public ResponseValue(GetCharactersResponse getCharactersResponse) {
            this.getCharactersResponse = getCharactersResponse;
        }

        public GetCharactersResponse getCharactersResponse() {
            return getCharactersResponse;
        }
    }
}
