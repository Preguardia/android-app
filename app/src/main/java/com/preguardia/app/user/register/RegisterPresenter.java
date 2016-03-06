package com.preguardia.app.user.register;

import android.support.annotation.NonNull;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import net.grandcentrix.tray.TrayAppPreferences;

import java.util.Map;

/**
 * @author amouly on 3/5/16.
 */
public class RegisterPresenter implements RegisterContract.UserActionsListener {

    @NonNull
    private final RegisterContract.View registerView;
    @NonNull
    private final Firebase firebase;
    @NonNull
    private final TrayAppPreferences appPreferences;

    public RegisterPresenter(@NonNull Firebase firebase, @NonNull TrayAppPreferences appPreferences,
                             @NonNull RegisterContract.View registerView) {
        this.firebase = firebase;
        this.appPreferences = appPreferences;
        this.registerView = registerView;
        this.registerView.setUserActionListener(this);
    }

    @Override
    public void registerUser(String type, String name, String email, String password, String birthDate,
                             String social, String plate, String phone) {


        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !birthDate.isEmpty()) {
            registerView.showProgress();

            // Send data to Firebase
            firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {
                    System.out.println("Successfully created user account with uid: " + result.get("uid"));

                    registerView.hideProgress();
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    // there was an error

                    System.out.println("FIREBASE ERROR: " + firebaseError.getMessage());

                    registerView.hideProgress();
                }
            });
        } else {
            registerView.showError();
        }

    }
}
