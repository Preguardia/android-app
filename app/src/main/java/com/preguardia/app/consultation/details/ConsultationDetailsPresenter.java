package com.preguardia.app.consultation.details;

import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.logger.Logger;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.consultation.model.GenericMessage;
import com.preguardia.app.general.Constants;
import com.preguardia.app.user.model.Medic;
import com.preguardia.app.user.model.Patient;

import net.grandcentrix.tray.TrayAppPreferences;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author amouly on 3/11/16.
 */
public class ConsultationDetailsPresenter implements ConsultationDetailsContract.Presenter {

    @NonNull
    private final ConsultationDetailsContract.View view;
    @NonNull
    private final Firebase consultationRef;
    @NonNull
    private final Firebase messagesRef;
    @NonNull
    private final Firebase tasksRef;
    @NonNull
    private final TrayAppPreferences appPreferences;

    private final String currentUserName;
    private final String consultationId;
    private final String currentUserType;
    private ChildEventListener messagesListener;

    private String medicId;
    private String patientId;

    public ConsultationDetailsPresenter(@NonNull Firebase firebase,
                                        @NonNull TrayAppPreferences appPreferences,
                                        @NonNull ConsultationDetailsContract.View detailsView,
                                        @NonNull String consultationId) {
        this.appPreferences = appPreferences;
        this.view = detailsView;
        this.consultationId = consultationId;

        this.messagesRef = firebase.child(Constants.FIREBASE_MESSAGES).child(this.consultationId);
        this.consultationRef = firebase.child(Constants.FIREBASE_CONSULTATIONS).child(this.consultationId);
        this.tasksRef = firebase.child(Constants.FIREBASE_QUEUE).child(Constants.FIREBASE_TASKS);

        this.currentUserType = appPreferences.getString(Constants.PREFERENCES_USER_TYPE, null);
        this.currentUserName = appPreferences.getString(Constants.PREFERENCES_USER_NAME, null);

        // Listen to changes on Consultation
        consultationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Consultation consultation = dataSnapshot.getValue(Consultation.class);

                Patient patient = consultation.getPatient();
                Medic medic = consultation.getMedic();

                patientId = patient.getId();
                medicId = medic.getId();

                // Handle each type of User
                if (currentUserType != null) {

                    switch (currentUserType) {
                        case Constants.FIREBASE_USER_TYPE_MEDIC:
                            // Show specific for Medic
                            view.showUserName(patient.getName());
                            view.showUserDesc(patient.getMedical());
                            view.showMedicActions();

                            break;

                        case Constants.FIREBASE_USER_TYPE_PATIENT:
                            // Show specific for Patient
                            view.showUserName(medic.getName());
                            view.showUserDesc(medic.getPlate());
                            view.showPatientActions();

                            break;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void sendMessage(String message) {
        if (!message.isEmpty()) {
            GenericMessage genericMessage = new GenericMessage(message, "text", currentUserType);

            // Push message to Firebase with generated ID
            messagesRef.push().setValue(genericMessage);

            // Clear input view and hide keyboard
            view.clearInput();
            view.toggleKeyboard();

            // Create Task with data
            Map<String, String> task = new HashMap<>();
            task.put(Constants.FIREBASE_TASK_TYPE, Constants.FIREBASE_TASK_TYPE_MESSAGE_NEW);
            task.put(Constants.FIREBASE_TASK_CONTENT, "Mensaje enviado por: " + currentUserName);
            task.put(Constants.FIREBASE_CONSULTATION_ID, consultationId);

            // Handle each type of User
            switch (currentUserType) {
                case Constants.FIREBASE_USER_TYPE_MEDIC:

                    // Send notification to Patient
                    task.put(Constants.FIREBASE_USER_ID, patientId);

                    break;

                case Constants.FIREBASE_USER_TYPE_PATIENT:

                    // Send notification to Medic
                    task.put(Constants.FIREBASE_USER_ID, medicId);

                    break;
            }

            // Push task to be processed
            tasksRef.push().setValue(task);

            if (BuildConfig.DEBUG) {
                Logger.d("New Consultation data saved successfully.");
            }
        }
    }

    @Override
    public void sendPicture() {

    }

    @Override
    public void loadItems() {

        if (BuildConfig.DEBUG) {
            Logger.d("Load Consultation Messages - ID: " + consultationId);
        }

        view.configureAdapter(currentUserType);

        // Handle new messages from Firebase
        messagesListener = messagesRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                // Convert Firebase object to POJO
                final GenericMessage model = snapshot.getValue(GenericMessage.class);

                // Add item to Messages list
                view.addItem(model);
                //view.hideLoading();

                if (BuildConfig.DEBUG) {
                    Logger.d("New message received: " + snapshot.getValue().toString());
                }
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

            }
        });
    }

    @Override
    public void closeConsultation() {
        view.showLoading();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(Constants.FIREBASE_CONSULTATION_STATUS, Constants.FIREBASE_CONSULTATION_STATUS_CLOSED);

        // Update Consultation status
        consultationRef.updateChildren(attributes, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                // Create Task with data
                Map<String, String> task = new HashMap<>();
                task.put(Constants.FIREBASE_TASK_TYPE, Constants.FIREBASE_TASK_TYPE_CONSULTATION_CLOSED);
                task.put(Constants.FIREBASE_PATIENT_ID, patientId);
                task.put(Constants.FIREBASE_MEDIC_ID, medicId);
                task.put(Constants.FIREBASE_CONSULTATION_ID, consultationId);

                // Push task to be processed
                tasksRef.push().setValue(task);

                view.hideLoading();
                view.onClose();

                if (BuildConfig.DEBUG) {
                    Logger.d("Consultation Closed data saved successfully.");
                }
            }
        });


    }

    @Override
    public void stopListener() {
        messagesRef.removeEventListener(messagesListener);
    }

    @Override
    public void takePicture() throws IOException {

    }
}
