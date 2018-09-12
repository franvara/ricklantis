package com.franvara.ricklantis.domain.use_cases;

import com.franvara.ricklantis.data.repository.DataProvider;
import com.franvara.ricklantis.data.utils.LogUtils;
import com.franvara.ricklantis.domain.entities.BaseError;
import com.franvara.ricklantis.domain.entities.Character;
import com.franvara.ricklantis.domain.entities.DataCallback;
import com.franvara.ricklantis.domain.threading.UseCase;
import com.franvara.ricklantis.domain.use_cases.requests.GetCharacterDetailRequest;

public class GetCharacterDetailUseCase extends UseCase<GetCharacterDetailUseCase.RequestValues, GetCharacterDetailUseCase.ResponseValue>{

    private final DataProvider dataProvider;

    public GetCharacterDetailUseCase(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        dataProvider.getCharacterDetail(requestValues.getCharacterDetailRequest(),
                new DataCallback<Character, BaseError>() {
                    @Override
                    public void onSuccess(Character response) {
                        LogUtils.LogDebug("GetCharacterDetailUC", "onGetCharacterDetailSuccess");
                        getUseCaseCallback().onSuccess(new ResponseValue(response));
                    }

                    @Override
                    public void onFailure(BaseError errorBase) {
                        LogUtils.LogDebug("GetCharacterDetailUC", "onGetCharacterDetailFailure");
                        getUseCaseCallback().onError(errorBase.getErrorMessage());
                    }
                });

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final GetCharacterDetailRequest getCharacterDetailRequest;

        public RequestValues(GetCharacterDetailRequest getCharacterDetailRequest) {
            this.getCharacterDetailRequest = getCharacterDetailRequest;
        }

        public GetCharacterDetailRequest getCharacterDetailRequest() {
            return getCharacterDetailRequest;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Character getCharacterDetailResponse;

        public ResponseValue(Character getCharacterDetailResponse) {
            this.getCharacterDetailResponse = getCharacterDetailResponse;
        }

        public Character getCharactersResponse() {
            return getCharacterDetailResponse;
        }
    }
}
