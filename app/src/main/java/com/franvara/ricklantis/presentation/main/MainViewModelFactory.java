package com.franvara.ricklantis.presentation.main;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.franvara.ricklantis.domain.threading.UseCaseHandler;
import com.franvara.ricklantis.domain.use_cases.GetCharactersUseCase;
import com.franvara.ricklantis.presentation.base.BaseViewModelFactory;

public class MainViewModelFactory extends BaseViewModelFactory{

    private final GetCharactersUseCase getCharactersUseCase;

    public MainViewModelFactory(UseCaseHandler useCaseHandler,
                                GetCharactersUseCase getCharactersUseCase) {
        super(useCaseHandler);
        this.getCharactersUseCase = getCharactersUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(useCaseHandler, getCharactersUseCase);
    }

}
