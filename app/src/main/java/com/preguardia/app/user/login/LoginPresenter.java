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
    private final LoginContract.View loginView;
    @NonNull
    private final Firebase firebase;
    @NonNull
    private final TrayAppPreferences appPreferences;

    public LoginPresenter(@NonNull Firebase firebase, @NonNull TrayAppPreferences appPreferences,
                          @NonNull LoginContract.View loginView) {
        this.firebase = firebase;
        this.appPreferences = appPreferences;
        this.loginView = loginView;
    }

    @Override
    public void loginUser(String user, String password) {

        if (!user.isEmpty() && !password.isEmpty()) {
            loginView.showProgress();

            firebase.authWithPassword(user, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Logger.d("Login successfully - User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());

                    // Save information about user
                    appPreferences.put(Constants.PREFERENCES_USER_UID, authData.getUid());
                    appPreferences.put(Constants.PREFERENCES_USER_TOKEN, authData.getToken());

                    loginView.hideProgress();
                    loginView.openMain();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Logger.e("Login error - Message: " + firebaseError.getMessage());

                    loginView.showLoginError(firebaseError.getMessage());
                    loginView.hideProgress();
                }
            });

        } else {
            Logger.d("Empty fields.");

            loginView.showEmptyFieldError();
        }
    }

    @Override
    public void registerUser() {
        loginView.openRegister();
    }
}
