package com.preguardia.app.consultation.details;

import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.logger.Logger;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.data.model.Consultation;
import com.preguardia.app.data.model.GenericMessage;
import com.preguardia.app.data.model.Medic;
import com.preguardia.app.data.model.Patient;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author amouly on 3/11/16.
 */
public class ConsultationDetailsPresenter implements ConsultationDetailsContract.Presenter {

    @NonNull
    private final GetConsultationByIdUseCase getConsultationByIdUseCase;
    @NonNull
    private final GetMessagesByIdUseCase getMessagesByIdUseCase;
    @NonNull
    private final SendMessageUseCase sendMessageUseCase;
    @NonNull
    private final TrayAppPreferences appPreferences;
    private final String currentUserName;
    private final String currentUserType;
    private ConsultationDetailsContract.View view;
    @NonNull
    private Firebase tasksRef;
    private String consultationId;
    private ChildEventListener messagesListener;
    private ValueEventListener consultationListener;

    private String medicId;
    private String patientId;

    @Inject
    public ConsultationDetailsPresenter(@NonNull GetConsultationByIdUseCase getConsultationByIdUseCase,
                                        @NonNull GetMessagesByIdUseCase getMessagesByIdUseCase,
                                        @NonNull SendMessageUseCase sendMessageUseCase,
                                        @NonNull TrayAppPreferences appPreferences) {
        this.getConsultationByIdUseCase = getConsultationByIdUseCase;
        this.getMessagesByIdUseCase = getMessagesByIdUseCase;
        this.sendMessageUseCase = sendMessageUseCase;

        this.appPreferences = appPreferences;

        //this.tasksRef = firebase.child(Constants.FIREBASE_QUEUE).child(Constants.FIREBASE_TASKS);

        this.currentUserType = appPreferences.getString(Constants.PREFERENCES_USER_TYPE, null);
        this.currentUserName = appPreferences.getString(Constants.PREFERENCES_USER_NAME, null);
    }

    @Override
    public void init(String consultationId) {
        this.consultationId = consultationId;
    }

    @Override
    public void loadConsultation() {
        view.showLoading();

        // Listen to changes on Consultation
        consultationListener = getConsultationByIdUseCase.execute(consultationId, new ValueEventListener() {
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

                if (consultation.getStatus().equals(Constants.FIREBASE_CONSULTATION_STATUS_CLOSED)) {
                    view.invalidateMessageInput();
                    view.invalidateActions();
                }

                view.hideLoading();
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
            sendMessageUseCase.sendMessage(consultationId, genericMessage);

            // Clear input view
            view.clearInput();

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
    public void loadMessages() {
        view.configureAdapter(currentUserType);

        // Listen to Messages for specific Consultation
        messagesListener = getMessagesByIdUseCase.execute(consultationId, new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                // Convert Firebase object to POJO
                final GenericMessage model = snapshot.getValue(GenericMessage.class);

                // Add item to Messages list
                view.addItem(model);
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

        if (BuildConfig.DEBUG) {
            Logger.d("Load Consultation Messages - ID: " + consultationId);
        }
    }

    @Override
    public void closeConsultation() {
        view.showLoading();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(Constants.FIREBASE_CONSULTATION_STATUS, Constants.FIREBASE_CONSULTATION_STATUS_CLOSED);

        // Update Consultation status
//        consultationRef.updateChildren(attributes, new Firebase.CompletionListener() {
//            @Override
//            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                // Create Task with data
//                Map<String, String> task = new HashMap<>();
//                task.put(Constants.FIREBASE_TASK_TYPE, Constants.FIREBASE_TASK_TYPE_CONSULTATION_CLOSED);
//                task.put(Constants.FIREBASE_PATIENT_ID, patientId);
//                task.put(Constants.FIREBASE_MEDIC_ID, medicId);
//                task.put(Constants.FIREBASE_CONSULTATION_ID, consultationId);
//
//                // Push task to be processed
//                tasksRef.push().setValue(task);
//
//                view.hideLoading();
//                view.onClose();
//
//                if (BuildConfig.DEBUG) {
//                    Logger.d("Consultation Closed data saved successfully.");
//                }
//            }
//        });
    }

    @Override
    public void stopConsultationListener() {
        getConsultationByIdUseCase.stop(consultationListener);
    }

    @Override
    public void stopMessagesListener() {
        getMessagesByIdUseCase.stop(messagesListener);
    }

    @Override
    public void attachView(ConsultationDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void takePicture() throws IOException {

    }
}
