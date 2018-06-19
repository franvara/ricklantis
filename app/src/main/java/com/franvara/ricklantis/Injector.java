package com.franvara.ricklantis;

import android.content.Context;
import android.support.annotation.NonNull;

import com.franvara.ricklantis.data.repository.DataProvider;
import com.franvara.ricklantis.data.repository.local.LocalDataSource;
import com.franvara.ricklantis.data.repository.memory.MemoryDataSource;
import com.franvara.ricklantis.data.repository.remote.RemoteDataSource;
import com.franvara.ricklantis.domain.threading.UseCaseHandler;
import com.franvara.ricklantis.domain.use_cases.GetCharactersUseCase;
import com.franvara.ricklantis.domain.use_cases.SetConnectionStateUseCase;
import com.franvara.ricklantis.presentation.ConnectivityChangeReceiver;
import com.franvara.ricklantis.presentation.main.MainViewModelFactory;

public class Injector {

    static DataProvider provideDataSource(@NonNull Context context) {
        return DataProvider.getInstance(
                new RemoteDataSource(),
                new LocalDataSource(context),
                new MemoryDataSource());
    }

    private static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static ConnectivityChangeReceiver provideConnectivityChangeReceiver(
            @NonNull Context context) {
        return ConnectivityChangeReceiver.getInstance(
                provideUseCaseHandler(),
                provideSetConnectionStateUseCase(context));
    }

    private static SetConnectionStateUseCase provideSetConnectionStateUseCase(
            @NonNull Context context) {
        return new SetConnectionStateUseCase(provideDataSource(context));
    }

    public static MainViewModelFactory provideMainViewModelFactory(@NonNull Context context) {
        return new MainViewModelFactory(provideUseCaseHandler(), provideGetCharactersUseCase(context));
    }

    private static GetCharactersUseCase provideGetCharactersUseCase(@NonNull Context context) {
        return new GetCharactersUseCase(provideDataSource(context));
    }

}
