package com.franvara.ricklantis.presentation.base;

import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.franvara.ricklantis.R;
import com.franvara.ricklantis.domain.entities.ConnectionState;

import butterknife.BindColor;
import butterknife.BindView;

import static com.franvara.ricklantis.domain.entities.ConnectionState.NOT_CONNECTED;

public abstract class BaseActivity extends AppCompatActivity implements ICommonUIMethods,
        IConnectionChangeListener{

    @BindView(R.id.fl_progress_container)
    FrameLayout flProgressContainer;
    @BindColor(R.color.red) int colorRojo;
    private Snackbar notConnectedSnackbar;

    //region ICommonUIMethods implementation

    public void showCommonProgress() {
        flProgressContainer.setVisibility(View.VISIBLE);
    }

    public void hideCommonProgress() {
        flProgressContainer.setVisibility(View.GONE);
    }

    public void showCommonToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showCommonSnackbar(String message) {
        final boolean isConnectionSnackbarShown = isConnectionSnackbarShown();
        Snackbar.make(flProgressContainer, message, Snackbar.LENGTH_LONG)
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (isConnectionSnackbarShown) {
                            notConnectedSnackbar.show();
                        }
                    }
                })
                .show();
    }

    public void showCommonSnackbar(String message, @IntegerRes int length, String buttonText,
                                   View.OnClickListener action) {
        final boolean isConnectionSnackbarShown = isConnectionSnackbarShown();
        Snackbar.make(flProgressContainer, message, length)
                .setAction(buttonText, action)
                .setActionTextColor(colorRojo)
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if(isConnectionSnackbarShown) {
                            notConnectedSnackbar.show();
                        }
                    }
                })
                .show();
    }

    @Override
    public void showNotConnectedSnackbar() {
        notConnectedSnackbar = Snackbar.make(flProgressContainer,
                getString(R.string.disconnected),
                Snackbar.LENGTH_LONG);
        notConnectedSnackbar.show();
    }

    @Override
    public void hideNotConnectedSnackbar() {
        if(notConnectedSnackbar != null) {
            notConnectedSnackbar.dismiss();
        }
    }

    //endregion

    //region IConnectionChangeListener implementation

    @Override
    public void toogleConnectionSnackbar(ConnectionState connectionState) {
        if (connectionState == NOT_CONNECTED) {
            showNotConnectedSnackbar();
        } else {
            hideNotConnectedSnackbar();
        }
    }

    @Override
    public abstract void onConnectionRestored();

    //endregion

    //region Private

    private boolean isConnectionSnackbarShown() {
        boolean isConnectionSnackbarShown = false;
        if(notConnectedSnackbar != null) {
            isConnectionSnackbarShown = notConnectedSnackbar.isShown();
        }
        return isConnectionSnackbarShown;
    }

    //endregion
}
