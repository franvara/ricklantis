package com.franvara.ricklantis.presentation.base;

import android.support.annotation.IntegerRes;
import android.view.View;

public interface ICommonUIMethods {

    void showCommonToast(String error);
    void showCommonSnackbar(String message);
    void showCommonSnackbar(String message, @IntegerRes int length,
                            String buttonText, View.OnClickListener action);
    void showNotConnectedSnackbar();
    void hideNotConnectedSnackbar();
}