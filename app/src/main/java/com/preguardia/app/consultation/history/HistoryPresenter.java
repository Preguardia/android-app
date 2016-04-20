package com.preguardia.app.consultation.history;

import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
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

import javax.inject.Inject;

/**
 * @author amouly on 3/10/16.
 */
public class HistoryPresenter implements HistoryContract.Presenter {

    @NonNull
    private GetConsultationsUseCase getConsultationsUseCase;

    private HistoryContract.View view;
    private String currentUserId;
    private String currentUserType;
    private ValueEventListener consultationsListener;

    @Inject
    public HistoryPresenter(@NonNull GetConsultationsUseCase getConsultationsUseCase,
                            @NonNull TrayAppPreferences appPreferences) {
        this.getConsultationsUseCase = getConsultationsUseCase;

        // Request User Profile
        this.currentUserId = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);
        this.currentUserType = appPreferences.getString(Constants.PREFERENCES_USER_TYPE, null);
    }

    @Override
    public void loadItems() {
        view.showLoading();

        this.view.configAdapter(currentUserType);

        String orderBy = null;

        switch (currentUserType) {
            case Constants.FIREBASE_USER_TYPE_MEDIC:

                orderBy = Constants.FIREBASE_USER_MEDIC_ID;

                break;

            case Constants.FIREBASE_USER_TYPE_PATIENT:

                orderBy = Constants.FIREBASE_USER_PATIENT_ID;

                break;
        }

        consultationsListener = getConsultationsUseCase.execute(orderBy, currentUserId, new ValueEventListener() {
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
        // Stop listener for Consultation changes
        getConsultationsUseCase.stop(consultationsListener);
    }

    @Override
    public void attachView(HistoryContract.View view) {
        this.view = view;
    }
}
