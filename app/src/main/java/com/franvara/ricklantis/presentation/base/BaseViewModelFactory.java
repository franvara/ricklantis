package com.franvara.ricklantis.presentation.base;

import android.arch.lifecycle.ViewModelProvider;

import com.franvara.ricklantis.domain.threading.UseCaseHandler;


public class BaseViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    protected final UseCaseHandler useCaseHandler;

    protected BaseViewModelFactory(UseCaseHandler useCaseHandler) {
        this.useCaseHandler = useCaseHandler;
    }
}
