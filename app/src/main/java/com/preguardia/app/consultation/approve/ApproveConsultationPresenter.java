package com.preguardia.app.consultation.approve;

import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.base.Joiner;
import com.orhanobut.logger.Logger;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.R;
import com.preguardia.app.data.model.Consultation;
import com.preguardia.app.data.model.Medic;
import com.preguardia.app.data.model.Patient;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author amouly on 3/17/16.
 */
public class ApproveConsultationPresenter implements ApproveConsultationContract.Presenter {

    @NonNull
    private final ApproveConsultationContract.View view;
    @NonNull
    private final Firebase consultationRef;
    @NonNull
    private final Firebase tasksRef;
    @NonNull
    private final TrayAppPreferences appPreferences;

    private final String consultationId;
    private Consultation consultation;

    private String currentUserId;
    private String currentUserName;
    private String currentMedicPlate;
    private ValueEventListener consultationListener;

    public ApproveConsultationPresenter(@NonNull Firebase firebase,
                                        @NonNull TrayAppPreferences appPreferences,
                                        @NonNull ApproveConsultationContract.View view,
                                        @NonNull String consultationId) {
        this.appPreferences = appPreferences;
        this.view = view;
        this.consultationId = consultationId;

        this.consultationRef = firebase.child(Constants.FIREBASE_CONSULTATIONS).child(this.consultationId);
        this.tasksRef = firebase.child(Constants.FIREBASE_QUEUE).child(Constants.FIREBASE_TASKS);

        this.currentUserId = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);
        this.currentUserName = appPreferences.getString(Constants.PREFERENCES_USER_NAME, null);
        this.currentMedicPlate = appPreferences.getString(Constants.PREFERENCES_USER_MEDIC_PLATE, null);
    }

    @Override
    public void loadConsultation() {

        if (BuildConfig.DEBUG) {
            Logger.d("Load consultation for approve - ID: " + consultationId);
        }

        view.showLoading();

        // Listen changes on selected Consultation
        consultationListener = consultationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                consultation = dataSnapshot.getValue(Consultation.class);

                // Set Patient data
                final Patient patient = consultation.getPatient();
                String patientName = patient.getName();
                String patientMedical = patient.getMedical();
                String patientBirth = patient.getBirthDate();

                // Calculate Patient age
                DateTime birthdate = new DateTime(patientBirth);
                Years age = Years.yearsBetween(birthdate, new DateTime());
                String ageFormatted = age.getYears() + " años";

                // TODO: replace with custom avatar
                if ((patientName != null) && (patientMedical != null)) {
                    view.showPatientInfo(patientName, ageFormatted, patientMedical, "http://media.graciasdoc.com/pictures/user_placeholder.png");
                }

                view.showCategory(consultation.getCategory());
                view.showPatient(consultation.getDetails().getPatient());
                view.showDescription(consultation.getDetails().getDescription());
                view.showFrequency(consultation.getDetails().getTime());

                List<String> medicationsList = consultation.getDetails().getMedications();
                List<String> allergiesList = consultation.getDetails().getAllergies();
                List<String> symptomsList = consultation.getDetails().getSymptoms();
                List<String> conditionsList = consultation.getDetails().getConditions();

                if (medicationsList != null) {
                    String medications = Joiner.on(", ").skipNulls().join(medicationsList);

                    view.showMedications(medications);
                }

                if (allergiesList != null) {
                    String allergies = Joiner.on(", ").skipNulls().join(allergiesList);

                    view.showAllergies(allergies);
                }

                if (symptomsList != null) {
                    String symptoms = Joiner.on(", ").skipNulls().join(symptomsList);

                    view.showSymptoms(symptoms);
                }

                if (conditionsList != null) {
                    String conditions = Joiner.on(", ").skipNulls().join(conditionsList);

                    view.showConditions(conditions);
                }

                view.hideLoading();
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

                // Show take Error
                view.showMessage(R.string.consultation_taken_error);

                break;

            case Constants.FIREBASE_CONSULTATION_STATUS_PENDING:
                view.showLoading();

                // Set Medic data
                final Medic medic = new Medic();
                medic.setId(currentUserId);
                medic.setName(currentUserName);
                medic.setPlate(currentMedicPlate);

                Map<String, Object> attributes = new HashMap<>();
                attributes.put(Constants.FIREBASE_CONSULTATION_STATUS, Constants.FIREBASE_CONSULTATION_STATUS_ASSIGNED);

                // Set Medic information
                consultationRef.child(Constants.FIREBASE_USER_TYPE_MEDIC).setValue(medic);

                // Update Consultation status
                consultationRef.updateChildren(attributes, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        view.hideLoading();
                        view.showMessage(R.string.consultation_taken_success);

                        // Create Task with data
                        Map<String, String> task = new HashMap<>();
                        task.put(Constants.FIREBASE_TASK_TYPE, Constants.FIREBASE_TASK_TYPE_CONSULTATION_APPROVED);
                        task.put("content", "Su consulta fue tomada por " + currentUserName);
                        task.put(Constants.FIREBASE_PATIENT_ID, consultation.getPatient().getId());
                        task.put(Constants.FIREBASE_CONSULTATION_ID, consultationId);

                        // Push task to be processed
                        tasksRef.push().setValue(task);

                        if (BuildConfig.DEBUG) {
                            Logger.d("Consultation Approved data saved successfully.");
                        }
                    }
                });

                break;
        }
    }

    @Override
    public void stopListener() {
        consultationRef.removeEventListener(consultationListener);
    }
}
