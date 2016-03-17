package com.preguardia.app.main;

import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.logger.Logger;
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
        String userToken = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);

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

                    Logger.d("User profile loaded - Name: " + userName);

                    mainView.showUserName(userName);

                    if (userType.equals(Constants.FIREBASE_USER_TYPE_MEDIC)) {
                        mainView.showUserDesc(user.getPlate());
                    } else {
                        mainView.showUserDesc(user.getMedical());
                    }

                    mainView.showUserPicture(userPicture);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mainView.hideLoading();
        }
    }
}
