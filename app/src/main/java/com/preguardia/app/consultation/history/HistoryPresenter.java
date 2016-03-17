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


        consultationsRef
                .orderByChild("patientId")
                .equalTo(this.currentUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        System.out.println(snapshot.toString());

                        GenericTypeIndicator<Map<String, Consultation>> mapType = new GenericTypeIndicator<Map<String, Consultation>>() {
                        };

                        Map<String, Consultation> consultations = snapshot.getValue(mapType);

                        if (consultations != null) {
                            for (Consultation consultation : consultations.values()) {
                                // Show item on list
                                historyView.addItem(consultation);
                            }

                            Logger.d("Consultations loaded - Size: " + consultations.size());

                        } else {
                            Logger.d("Consultations no results");
                        }

                        historyView.hideLoading();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

//        // Show Consultations for current user
//        consultationsRef.orderByChild("patientId").equalTo(this.currentUserId).addChildEventListener(new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
//            }
//        });
    }
}
