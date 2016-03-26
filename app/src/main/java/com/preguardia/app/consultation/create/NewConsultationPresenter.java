package com.preguardia.app.consultation.create;

import android.support.annotation.NonNull;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.orhanobut.logger.Logger;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author amouly on 3/9/16.
 */
public class NewConsultationPresenter implements NewConsultationContract.Presenter {

    @NonNull
    private final NewConsultationContract.View consultationView;
    @NonNull
    private final Firebase consultationsRef;
    @NonNull
    private final Firebase tasksRef;
    @NonNull
    final TrayAppPreferences appPreferences;

    private final String currentUserId;
    private final String currentUserName;
    private final String patientMedical;
    private String patientBirthDate;

    public NewConsultationPresenter(@NonNull Firebase firebase, @NonNull TrayAppPreferences appPreferences,
                                    @NonNull NewConsultationContract.View consultationView) {
        this.consultationsRef = firebase.child(Constants.FIREBASE_CONSULTATIONS);
        this.tasksRef = firebase.child(Constants.FIREBASE_QUEUE).child(Constants.FIREBASE_TASKS);

        this.appPreferences = appPreferences;
        this.consultationView = consultationView;

        currentUserId = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);
        currentUserName = appPreferences.getString(Constants.PREFERENCES_USER_NAME, null);
        patientMedical = appPreferences.getString(Constants.PREFERENCES_USER_PATIENT_MEDICAL, null);
        patientBirthDate = appPreferences.getString(Constants.PREFERENCES_USER_BIRTH, null);
    }

    @Override
    public void takePicture() throws IOException {

    }

    @Override
    public void saveConsultation(String category, String summary, String details) {
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();

        // Request environment attributes
        final String currentTime = fmt.print(new DateTime());

        consultationView.showLoading();

        // Create a Pending Consultation
        Consultation consultation = new Consultation();

        // Set Patient data
        consultation.setPatientId(currentUserId);
        consultation.setPatientName(currentUserName);
        consultation.setPatientMedical(patientMedical);
        consultation.setPatientBirthDate(patientBirthDate);

        // Set Consultation data
        consultation.setDateCreated(currentTime);
        consultation.setStatus(Constants.FIREBASE_CONSULTATION_STATUS_PENDING);
        consultation.setSummary(summary);
        consultation.setCategory(category);
        consultation.setDetails(details);

        Firebase newConsultation = consultationsRef.push();

        // Save generated ID
        final String consultationId = newConsultation.getKey();

        // Send object to Firebase
        newConsultation.setValue(consultation, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    consultationView.hideLoading();
                    consultationView.showErrorMessage("Error de servicio.");

                    if (BuildConfig.DEBUG) {
                        Logger.e("Error creating New Consultation - " + firebaseError.getMessage());
                    }
                } else {
                    consultationView.hideLoading();
                    consultationView.showSuccess();

                    // Push Consultation to be processed
                    Map<String, String> task = new HashMap<>();
                    task.put("type", "new-consultation");
                    task.put("message", "Consulta enviada por " + currentUserName);
                    task.put(Constants.FIREBASE_CONSULTATION_ID, consultationId);
                    task.put("topic", "medics");
                    tasksRef.push().setValue(task);

                    if (BuildConfig.DEBUG) {
                        Logger.d("New Consultation data saved successfully.");
                    }
                }
            }
        });

    }
}
