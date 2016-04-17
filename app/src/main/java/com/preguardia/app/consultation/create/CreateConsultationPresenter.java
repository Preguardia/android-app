package com.preguardia.app.consultation.create;

import android.support.annotation.NonNull;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.orhanobut.logger.Logger;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.R;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.consultation.model.Details;
import com.preguardia.app.general.Constants;
import com.preguardia.app.user.model.Patient;

import net.grandcentrix.tray.TrayAppPreferences;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author amouly on 3/9/16.
 */
public class CreateConsultationPresenter implements CreateConsultationContract.Presenter {

    @NonNull
    final TrayAppPreferences appPreferences;
    @NonNull
    private final CreateConsultationContract.View consultationView;
    @NonNull
    private final Firebase consultationsRef;
    @NonNull
    private final Firebase tasksRef;

    private final String currentUserName;

    private Consultation consultation;
    private Patient patient;

    public CreateConsultationPresenter(@NonNull Firebase firebase,
                                       @NonNull TrayAppPreferences appPreferences,
                                       @NonNull CreateConsultationContract.View consultationView) {
        this.consultationsRef = firebase.child(Constants.FIREBASE_CONSULTATIONS);
        this.tasksRef = firebase.child(Constants.FIREBASE_QUEUE).child(Constants.FIREBASE_TASKS);

        this.appPreferences = appPreferences;
        this.consultationView = consultationView;

        String currentUserId = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);
        currentUserName = appPreferences.getString(Constants.PREFERENCES_USER_NAME, null);
        String patientMedical = appPreferences.getString(Constants.PREFERENCES_USER_PATIENT_MEDICAL, null);
        String patientBirthDate = appPreferences.getString(Constants.PREFERENCES_USER_BIRTH, null);

        consultation = new Consultation();
        consultation.setDetails(new Details());
        patient = new Patient();

        // Set Patient data
        patient.setId(currentUserId);
        patient.setName(currentUserName);
        patient.setMedical(patientMedical);
        patient.setBirthDate(patientBirthDate);
    }

    @Override
    public void saveCategory(String category) {
        consultation.setCategory(category);
    }

    @Override
    public void savePatient(String patient) {

    }

    @Override
    public void saveDescription(String description) {
        consultation.getDetails().setDescription(description);

        System.out.println("SAVE DESCRIPTION: " + description);
    }

    @Override
    public void saveTime(String time) {
        consultation.getDetails().setTime(time);
    }

    @Override
    public void saveMedications(List<String> medications) {
        consultation.getDetails().setMedications(medications);
    }

    @Override
    public void saveAllergies(List<String> allergies) {
        consultation.getDetails().setAllergies(allergies);
    }

    @Override
    public void saveSymptoms(List<String> symptoms) {
        consultation.getDetails().setSymptoms(symptoms);
    }

    @Override
    public void saveConditions(List<String> conditions) {
        consultation.getDetails().setConditions(conditions);
    }

    @Override
    public void completeRequest() {

    }

    @Override
    public void saveConsultation() {
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();

        // Request environment attributes
        final String currentTime = fmt.print(new DateTime());

        consultationView.showLoading();

        // Set Consultation data
        consultation.setDateCreated(currentTime);
        consultation.setStatus(Constants.FIREBASE_CONSULTATION_STATUS_PENDING);
        consultation.setPatient(patient);

        Firebase newConsultation = consultationsRef.push();

        // Save generated ID
        final String consultationId = newConsultation.getKey();

        // Send object to Firebase
        newConsultation.setValue(consultation, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                if (firebaseError != null) {
                    consultationView.hideLoading();
                    consultationView.showErrorMessage(R.string.consultation_create_error);

                    if (BuildConfig.DEBUG) {
                        Logger.e("Error creating New Consultation - " + firebaseError.getMessage());
                    }
                } else {
                    consultationView.hideLoading();
                    consultationView.showSuccess();

                    // Create Task with data
                    Map<String, String> task = new HashMap<>();
                    task.put("type", "new-consultation");
                    task.put("content", "Consulta enviada por " + currentUserName);
                    task.put(Constants.FIREBASE_CONSULTATION_ID, consultationId);

                    // Push task to be processed
                    tasksRef.push().setValue(task);

                    if (BuildConfig.DEBUG) {
                        Logger.d("New Consultation data saved successfully.");
                    }
                }
            }
        });

    }
}
