package com.preguardia.app.consultation.history;

import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.orhanobut.logger.Logger;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

/**
 * @author amouly on 3/10/16.
 */
public class HistoryPresenter implements HistoryContract.UserActionsListener {

    @NonNull
    private final HistoryContract.View historyView;
    @NonNull
    private final Firebase consultationsRef;
    @NonNull
    private final TrayAppPreferences appPreferences;

    private final String currentUserId;

    public HistoryPresenter(@NonNull Firebase firebase,
                            @NonNull TrayAppPreferences appPreferences,
                            @NonNull HistoryContract.View view) {
        this.consultationsRef = firebase;
        this.appPreferences = appPreferences;
        this.historyView = view;

        this.currentUserId = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);
    }

    @Override
    public void loadItems() {
        historyView.showLoading();

        // Show Consultations for current user
        consultationsRef.orderByChild("patientId").equalTo(this.currentUserId).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Consultation consultation = dataSnapshot.getValue(Consultation.class);

                Logger.d("Consultation loaded: " + consultation.getCategory() + " - " + consultation.getSummary());

                // Show item on list
                historyView.addItem(consultation);
                historyView.hideLoading();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
}
