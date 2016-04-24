package com.preguardia.app.user.register;

import android.support.annotation.NonNull;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.orhanobut.logger.Logger;
import com.preguardia.app.R;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author amouly on 3/5/16.
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    @NonNull
    private final RegisterContract.View view;
    @NonNull
    private final Firebase firebase;
    @NonNull
    private final TrayAppPreferences appPreferences;

    public RegisterPresenter(@NonNull Firebase firebase,
                             @NonNull TrayAppPreferences appPreferences,
                             @NonNull RegisterContract.View registerView) {
        this.firebase = firebase;
        this.appPreferences = appPreferences;
        this.view = registerView;
    }

    @Override
    public void registerUser(final String type, final String name, final String email, final String password,
                             final String password2, final String birthDate, final String medical,
                             final String plate, final String phone) {

        if (password.equals(password2)) {
            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !birthDate.isEmpty()) {
                view.showProgress();

                // Send data to Firebase
                firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        final Object uuid = result.get("uid");

                        Logger.d("Successfully created user UID: " + result.get("uid"));

                        // Authentication just completed successfully
                        Map<String, String> map = new HashMap<>();

                        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
                        DateTime dateTime = formatter.parseDateTime(birthDate);
                        String formattedDate = dateTime.toDateTimeISO().toString();

                        // Save user attributes
                        map.put("name", name);
                        map.put("birthDate", formattedDate);
                        map.put("type", type);
                        map.put("medical", medical);
                        map.put("plate", plate);
                        map.put("phone", phone);
                        // TODO: Change with user picture
                        map.put("picture", "http://media.graciasdoc.com/pictures/user_placeholder.png");

                        // Send to Firebase
                        firebase.child(Constants.FIREBASE_USERS)
                                .child(uuid.toString())
                                .setValue(map);

                        view.hideProgress();
                        view.showSuccess();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // There was an error
                        Logger.e("Firebase register error: " + firebaseError.getMessage());

                        view.hideProgress();
                        view.showError(R.string.user_register_service_error);
                    }
                });
            } else {
                Logger.d("Empty field");

                view.showError(R.string.user_register_incomplete_fields);
            }
        } else {
            Logger.d("Password doesn't match");

            view.showError(R.string.user_register_password_not_match);
        }
    }
}
