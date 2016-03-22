package com.preguardia.app.consultation.approve;

import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.logger.Logger;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.HashMap;
import java.util.Map;

/**
 * @author amouly on 3/17/16.
 */
public class ApproveConsultationPresenter implements ApproveConsultationContract.Presenter {

    @NonNull
    private final ApproveConsultationContract.View approveView;
    @NonNull
    private final Firebase consultationRef;
    @NonNull
    private final TrayAppPreferences appPreferences;

    private final String consultationId;
    private Consultation consultation;

    private String currentUserId;
    private String currentUserName;
    private String currentMedicPlate;

    public ApproveConsultationPresenter(@NonNull Firebase consultationsRef,
                                        @NonNull TrayAppPreferences appPreferences,
                                        @NonNull ApproveConsultationContract.View approveView,
                                        @NonNull String consultationId) {
        this.appPreferences = appPreferences;
        this.approveView = approveView;
        this.consultationId = consultationId;

        this.consultationRef = consultationsRef.child(this.consultationId);

        this.currentUserId = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);
        this.currentUserName = appPreferences.getString(Constants.PREFERENCES_USER_NAME, null);
        this.currentMedicPlate = appPreferences.getString(Constants.PREFERENCES_USER_MEDIC_PLATE, null);
    }

    @Override
    public void loadConsultation() {

        if (BuildConfig.DEBUG) {
            Logger.d("Load consultation for approve - ID: " + consultationId);
        }

        approveView.showLoading();

        consultationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                consultation = dataSnapshot.getValue(Consultation.class);

                String patientName = consultation.getPatientName();
                String patientMedical = consultation.getPatientMedical();
                String patientBirth = consultation.getPatientBirthDate();

                // Calculate Patient age
                DateTime birthdate = new DateTime(patientBirth);
                DateTime now = new DateTime();
                Years age = Years.yearsBetween(birthdate, now);

                String ageFormatted = age.getYears() + " años";

                if ((patientName != null) && (patientMedical != null)) {
                    approveView.showPatientInfo(patientName, ageFormatted, patientMedical, null);
                }

                String category = consultation.getCategory();
                String summary = consultation.getSummary();
                String details = consultation.getDetails();

                approveView.showConsultationInfo(category, summary, details);

                approveView.hideLoading();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public void takeConsultation() {
        String status = consultation.getStatus();

        switch (status) {

            case Constants.FIREBASE_CONSULTATION_STATUS_ASSIGNED:

                approveView.showMessage("Error, la consulta ya fue tomada.");

                break;

            case Constants.FIREBASE_CONSULTATION_STATUS_PENDING:
                approveView.showLoading();

                Map<String, Object> attributes = new HashMap<>();
                attributes.put("medicId", currentUserId);
                attributes.put("medicName", currentUserName);
                attributes.put("medicPlate", currentMedicPlate);
                attributes.put("status", Constants.FIREBASE_CONSULTATION_STATUS_ASSIGNED);

                consultationRef.updateChildren(attributes, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        approveView.hideLoading();
                        approveView.showMessage("Consulta tomada exitosamente.");
                    }
                });

                break;
        }
    }
}
