package com.preguardia.app.user.login;

import android.support.annotation.NonNull;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.orhanobut.logger.Logger;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

/**
 * @author amouly on 3/5/16.
 */
public class LoginPresenter implements LoginContract.Presenter {

    @NonNull
    private final Firebase firebase;
    @NonNull
    private final TrayAppPreferences appPreferences;
    private LoginContract.View view;

    public LoginPresenter(@NonNull Firebase firebase, @NonNull TrayAppPreferences appPreferences) {
        this.firebase = firebase;
        this.appPreferences = appPreferences;
    }

    @Override
    public void loginUser(String user, String password) {

        if (!user.isEmpty() && !password.isEmpty()) {
            view.showProgress();

            firebase.authWithPassword(user, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Logger.d("Login successfully - User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());

                    // Save information about user
                    appPreferences.put(Constants.PREFERENCES_USER_UID, authData.getUid());
                    appPreferences.put(Constants.PREFERENCES_USER_TOKEN, authData.getToken());

                    view.hideProgress();
                    view.openMain();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Logger.e("Login error - Message: " + firebaseError.getMessage());

                    view.showLoginError(firebaseError.getMessage());
                    view.hideProgress();
                }
            });

        } else {
            Logger.d("Empty fields.");

            view.showEmptyFieldError();
        }
    }

    @Override
    public void registerUser() {
        view.openRegister();
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
    }
}
