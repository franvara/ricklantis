package com.franvara.ricklantis.presentation.detail;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.franvara.ricklantis.domain.threading.UseCaseHandler;
import com.franvara.ricklantis.domain.use_cases.GetCharacterDetailUseCase;
import com.franvara.ricklantis.presentation.base.BaseViewModelFactory;

public class DetailViewModelFactory extends BaseViewModelFactory {

    private final GetCharacterDetailUseCase getCharacterDetailUseCase;

    public DetailViewModelFactory(UseCaseHandler useCaseHandler,
                                  GetCharacterDetailUseCase getCharacterDetailUseCase) {
        super(useCaseHandler);
        this.getCharacterDetailUseCase = getCharacterDetailUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(useCaseHandler, getCharacterDetailUseCase);
    }

}
