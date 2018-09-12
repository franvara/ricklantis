package com.franvara.ricklantis.data.repository;

import com.franvara.ricklantis.data.repository.local.ILocalDataSource;
import com.franvara.ricklantis.data.repository.memory.IMemoryDataSource;
import com.franvara.ricklantis.data.repository.remote.IRemoteDataSource;
import com.franvara.ricklantis.domain.DataSource;
import com.franvara.ricklantis.domain.entities.BaseError;
import com.franvara.ricklantis.domain.entities.Character;
import com.franvara.ricklantis.domain.entities.ConnectionState;
import com.franvara.ricklantis.domain.entities.DataCallback;
import com.franvara.ricklantis.domain.use_cases.requests.GetCharacterDetailRequest;
import com.franvara.ricklantis.domain.use_cases.requests.GetCharactersRequest;
import com.franvara.ricklantis.domain.use_cases.responses.GetCharactersResponse;

public class DataProvider implements DataSource {

    //region Fields
    private static DataProvider INSTANCE = null;
    private final IRemoteDataSource remoteDataSource;
    private final ILocalDataSource localDataSource;
    private final IMemoryDataSource memoryDataSource;
    //endregion

    //region Constructor & Instanciator

    private DataProvider(IRemoteDataSource remoteDataSource, ILocalDataSource localDataSource,
                         IMemoryDataSource memoryDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.memoryDataSource = memoryDataSource;
    }

    public static synchronized DataProvider getInstance(IRemoteDataSource remoteDataSource,
                                                        ILocalDataSource localDataSource,
                                                        IMemoryDataSource memoryDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DataProvider(remoteDataSource, localDataSource, memoryDataSource);
        }
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public static void destroyInstance() {
        INSTANCE = null;
    }

    //endregion

    //region DataSource implementation

    @Override
    public void setConnectionState(ConnectionState connectionState) {
        memoryDataSource.setConnectionState(connectionState);
    }

    @Override
    public void getCharacters(final GetCharactersRequest request,
                              final DataCallback<GetCharactersResponse, BaseError> callback) {

        if (memoryDataSource.getConnectionState() == ConnectionState.CONNECTED) {
            remoteDataSource.getCharacters(request, new DataCallback<GetCharactersResponse, BaseError>() {
                @Override
                public void onSuccess(GetCharactersResponse response) {

                    response.getInfo().setActual(request.getPage());

                    if (request.getPage() == 1) {
                        localDataSource.clearCharacters();
                    }

                    localDataSource.saveCharacters(response.getCharacters());

                    callback.onSuccess(response);

                }

                @Override
                public void onFailure(BaseError errorBase) {
                    callback.onFailure(errorBase);
                }
            });
        } else {
            callback.onSuccess(new GetCharactersResponse(localDataSource.getCharacters()));
        }

    }

    @Override
    public void getCharacterDetail(GetCharacterDetailRequest request,
                                   DataCallback<Character, BaseError> callback) {

        Character character = localDataSource.getCharacterDetail(request.getId());
        if (character != null) {
            callback.onSuccess(character);
        } else {
            callback.onFailure(BaseError.getStandardError());
        }
    }


    //endregion


}
