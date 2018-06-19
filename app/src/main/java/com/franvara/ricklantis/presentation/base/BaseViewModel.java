package com.franvara.ricklantis.presentation.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.franvara.ricklantis.domain.threading.UseCaseHandler;
import com.franvara.ricklantis.presentation.app_model.ShowState;

public class BaseViewModel extends ViewModel {

    protected final UseCaseHandler useCaseHandler;

    protected MutableLiveData<ShowState> commonProgressLayoutState = new MutableLiveData<ShowState>() {};
    protected MutableLiveData<String> snackbarMessage = new MutableLiveData<String>() {};

    protected BaseViewModel(UseCaseHandler useCaseHandler) {
        this.useCaseHandler = useCaseHandler;
    }

    public LiveData<ShowState> getCommonProgressLayoutState() {
        return commonProgressLayoutState;
    }

    public LiveData<String> getSnackbarMessage() {
        return snackbarMessage;
    }

}
