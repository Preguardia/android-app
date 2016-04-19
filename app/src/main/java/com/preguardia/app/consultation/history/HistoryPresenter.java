package com.preguardia.app.consultation.history;

import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.orhanobut.logger.Logger;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author amouly on 3/10/16.
 */
public class HistoryPresenter implements HistoryContract.Presenter {

    @NonNull
    private final HistoryContract.View view;
    @NonNull
    private final Firebase consultationsRef;

    private final String currentUserId;
    private final String currentUserType;
    private ValueEventListener consultationsListener;

    public HistoryPresenter(@NonNull Firebase firebase,
                            @NonNull TrayAppPreferences appPreferences,
                            @NonNull HistoryContract.View view) {
        this.consultationsRef = firebase;
        this.view = view;

        // Request User Profile
        this.currentUserId = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);
        this.currentUserType = appPreferences.getString(Constants.PREFERENCES_USER_TYPE, null);

        this.view.configAdapter(currentUserType);
    }

    @Override
    public void loadItems() {
        view.showLoading();

        String orderBy = null;

        switch (currentUserType) {
            case Constants.FIREBASE_USER_TYPE_MEDIC:

                orderBy = Constants.FIREBASE_USER_MEDIC_ID;

                break;

            case Constants.FIREBASE_USER_TYPE_PATIENT:

                orderBy = Constants.FIREBASE_USER_PATIENT_ID;

                break;
        }

        // Show Consultations for current user
        consultationsListener = consultationsRef
                .orderByChild(orderBy)
                .equalTo(currentUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        GenericTypeIndicator<Map<String, Consultation>> mapType = new GenericTypeIndicator<Map<String, Consultation>>() {
                        };

                        Map<String, Consultation> consultations = snapshot.getValue(mapType);

                        // Check empty list
                        if (consultations != null) {
                            Logger.d("Consultations loaded - Size: " + consultations.size());

                            List<Consultation> items = new ArrayList<>();

                            for (String key : consultations.keySet()) {
                                Consultation consultation = consultations.get(key);

                                // Set ID based on key
                                consultation.setId(key);

                                // Show item on list
                                items.add(consultation);
                            }

                            view.showItemList(items);
                            view.hideEmpty();
                            view.showResults();
                        } else {
                            Logger.d("Consultations no results");

                            view.hideResults();
                            view.showEmpty();
                        }

                        view.hideLoading();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
    }

    @Override
    public void stopListener() {
        consultationsRef.removeEventListener(consultationsListener);
    }
}
