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
    private final HistoryContract.View historyView;
    @NonNull
    private final Firebase consultationsRef;
    @NonNull
    private final TrayAppPreferences appPreferences;

    private final String currentUserId;
    private final String currentUserType;

    public HistoryPresenter(@NonNull Firebase firebase,
                            @NonNull TrayAppPreferences appPreferences,
                            @NonNull HistoryContract.View view) {
        this.consultationsRef = firebase;
        this.appPreferences = appPreferences;
        this.historyView = view;

        // Request User Profile
        this.currentUserId = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);
        this.currentUserType = appPreferences.getString(Constants.PREFERENCES_USER_TYPE, null);
    }

    @Override
    public void loadItems() {
        historyView.showLoading();

        String orderBy;

        historyView.configAdapter(currentUserType);

        if (currentUserType.equals(Constants.FIREBASE_USER_TYPE_MEDIC)) {
            orderBy = Constants.FIREBASE_USER_MEDIC_ID;
        } else {
            orderBy = Constants.FIREBASE_USER_PATIENT_ID;
        }

        // Show Consultations for current user
        consultationsRef
                .orderByChild(orderBy)
                .equalTo(this.currentUserId)
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

                            historyView.showItemList(items);
                            historyView.hideEmpty();
                            historyView.showResults();
                        } else {
                            Logger.d("Consultations no results");

                            historyView.hideResults();
                            historyView.showEmpty();
                        }

                        historyView.hideLoading();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
    }
}
