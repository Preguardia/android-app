package com.preguardia.app.user.register;

import android.support.annotation.NonNull;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import java.util.HashMap;
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
    public void registerUser(final String type, final String name, final String email, final String password,
                             final String birthDate, final String medical, final String plate, final String phone) {

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !birthDate.isEmpty()) {
            registerView.showProgress();

            // Send data to Firebase
            firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {
                    System.out.println("Successfully created user account with uid: " + result.get("uid"));
                    Object uuid = result.get("uid");

                    // Authentication just completed successfully
                    Map<String, String> map = new HashMap<>();

                    // Save user attributes
                    map.put("name", name);
                    map.put("birthDate", birthDate);
                    map.put("type", type);
                    map.put("medical", medical);
                    map.put("plate", plate);
                    map.put("phone", phone);

                    // Send to Firebase
                    firebase.child(Constants.FIREBASE_USERS).child(uuid.toString()).setValue(map);

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
