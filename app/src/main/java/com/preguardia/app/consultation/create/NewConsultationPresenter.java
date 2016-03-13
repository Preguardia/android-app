package com.preguardia.app.consultation.create;

import android.support.annotation.NonNull;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * @author amouly on 3/9/16.
 */
public class NewConsultationPresenter implements NewConsultationContract.UserActionsListener {

    @NonNull
    private final NewConsultationContract.View consultationView;
    @NonNull
    private final Firebase firebase;
    @NonNull
    final TrayAppPreferences appPreferences;

    public NewConsultationPresenter(@NonNull Firebase firebase, @NonNull TrayAppPreferences appPreferences,
                                    @NonNull NewConsultationContract.View consultationView) {
        this.firebase = firebase;
        this.appPreferences = appPreferences;
        this.consultationView = consultationView;
        this.consultationView.setUserActionListener(this);
    }

    @Override
    public void takePicture() throws IOException {

    }

    @Override
    public void saveConsultation(String category, String summary, String details) {
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();

        // Request environment attributes
        final String currentTime = fmt.print(new DateTime());
        final String userId = appPreferences.getString(Constants.PREFERENCES_USER_UID, null);

        consultationView.showLoading();

        // Create a Pending Consultation
        Consultation consultation = new Consultation(userId, currentTime,
                Constants.FIREBASE_CONSULTATION_STATUS_PENDING, summary, category, details);

        Firebase newConsultation = firebase.push();

        // Save generated ID
        final String consultationId = newConsultation.getKey();

        // Send object to Firebase
        newConsultation.setValue(consultation, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");

                    consultationView.hideLoading();
                    consultationView.showSuccess(consultationId);
                }
            }
        });

    }
}
