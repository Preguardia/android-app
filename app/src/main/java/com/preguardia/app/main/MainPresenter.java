package com.preguardia.app.main;

import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.logger.Logger;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.general.Constants;
import com.preguardia.app.user.model.User;

import net.grandcentrix.tray.TrayAppPreferences;

/**
 * @author amouly on 3/17/16.
 */
public class MainPresenter implements MainContract.Presenter {

    @NonNull
    private final MainContract.View mainView;
    @NonNull
    private final Firebase userFirebaseRef;
    @NonNull
    private final TrayAppPreferences appPreferences;

    public MainPresenter(@NonNull Firebase firebase, @NonNull TrayAppPreferences appPreferences,
                         @NonNull MainContract.View view) {
        this.userFirebaseRef = firebase;
        this.appPreferences = appPreferences;
        this.mainView = view;
    }

    @Override
    public void loadUserInfo() {
        final String userToken = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);

        mainView.showLoading();

        if (userToken != null) {
            userFirebaseRef.child(userToken).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    // Retrieve User attributes
                    String userName = user.getName();
                    String userType = user.getType();
                    String userPicture = user.getPicture();

                    mainView.showUserName(userName);
                    mainView.showUserPicture(userPicture);

                    if (userType.equals(Constants.FIREBASE_USER_TYPE_MEDIC)) {
                        // Populate Menu
                        mainView.showUserDesc(user.getPlate());
                        mainView.showMedicMenu();

                        // Save type of user
                        appPreferences.put(Constants.PREFERENCES_USER_TYPE, Constants.FIREBASE_USER_TYPE_MEDIC);

                        // Load History Fragment
                        mainView.loadHistorySection();

                        // TODO: Remove testing notification
                        mainView.showNotification();
                    } else if (userType.equals(Constants.FIREBASE_USER_TYPE_PATIENT)) {
                        // Populate Menu
                        mainView.showUserDesc(user.getMedical());
                        mainView.showPatientMenu();

                        // Save type of user
                        appPreferences.put(Constants.PREFERENCES_USER_TYPE, Constants.FIREBASE_USER_TYPE_PATIENT);

                        // Load New Consultation Fragment
                        mainView.loadNewConsultationSection();
                    }

                    mainView.hideLoading();

                    if (BuildConfig.DEBUG) {
                        Logger.d("User profile loaded - Name: " + userName + " - Type: " + userType);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }
}
