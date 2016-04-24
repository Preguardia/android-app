package com.preguardia.app.consultation.details;

import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.logger.Logger;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.consultation.details.domain.CloseConsultationUseCase;
import com.preguardia.app.consultation.details.domain.CreateConsultationClosedTaskUseCase;
import com.preguardia.app.consultation.details.domain.CreateNewMessageTaskUseCase;
import com.preguardia.app.consultation.details.domain.GetConsultationByIdUseCase;
import com.preguardia.app.consultation.details.domain.GetMessagesByIdUseCase;
import com.preguardia.app.consultation.details.domain.SendMessageUseCase;
import com.preguardia.app.data.model.Consultation;
import com.preguardia.app.data.model.GenericMessage;
import com.preguardia.app.data.model.Medic;
import com.preguardia.app.data.model.Patient;
import com.preguardia.app.general.Constants;
import com.preguardia.app.user.SessionManager;

import java.io.IOException;

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
    private final CreateNewMessageTaskUseCase createNewMessageTaskUseCase;
    @NonNull
    private final CloseConsultationUseCase closeConsultationUseCase;
    @NonNull
    private final CreateConsultationClosedTaskUseCase createConsultationClosedTaskUseCase;
    @NonNull
    private final SessionManager sessionManager;
    private final String currentUserName;
    private final String currentUserType;
    private ConsultationDetailsContract.View view;
    private ChildEventListener messagesListener;
    private ValueEventListener consultationListener;

    private String consultationId;
    private String medicId;
    private String patientId;

    @Inject
    public ConsultationDetailsPresenter(@NonNull GetConsultationByIdUseCase getConsultationByIdUseCase,
                                        @NonNull GetMessagesByIdUseCase getMessagesByIdUseCase,
                                        @NonNull SendMessageUseCase sendMessageUseCase,
                                        @NonNull CreateNewMessageTaskUseCase createNewMessageTaskUseCase,
                                        @NonNull CloseConsultationUseCase closeConsultationUseCase,
                                        @NonNull CreateConsultationClosedTaskUseCase createConsultationClosedTaskUseCase,
                                        @NonNull SessionManager sessionManager) {
        this.getConsultationByIdUseCase = getConsultationByIdUseCase;
        this.getMessagesByIdUseCase = getMessagesByIdUseCase;
        this.sendMessageUseCase = sendMessageUseCase;
        this.createNewMessageTaskUseCase = createNewMessageTaskUseCase;
        this.closeConsultationUseCase = closeConsultationUseCase;
        this.createConsultationClosedTaskUseCase = createConsultationClosedTaskUseCase;
        this.sessionManager = sessionManager;

        this.currentUserType = sessionManager.getUserType();
        this.currentUserName = sessionManager.getUserName();
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
                switch (currentUserType) {
                    case Constants.FIREBASE_USER_TYPE_MEDIC:
                        // Show specific for Medic
                        view.showUserName(patient.getName());
                        view.showUserDesc(patient.getMedical());

                        switch (consultation.getStatus()) {
                            case Constants.FIREBASE_CONSULTATION_STATUS_CLOSED:

                                view.invalidateMessageInput();
                                view.invalidateActions();

                                break;

                            case Constants.FIREBASE_CONSULTATION_STATUS_ASSIGNED:
                                view.showMedicActions();

                                break;
                        }

                        break;

                    case Constants.FIREBASE_USER_TYPE_PATIENT:
                        // Show specific for Patient
                        view.showUserName(medic.getName());
                        view.showUserDesc(medic.getPlate());

                        switch (consultation.getStatus()) {
                            case Constants.FIREBASE_CONSULTATION_STATUS_CLOSED:

                                view.invalidateMessageInput();
                                view.invalidateActions();

                                break;

                            case Constants.FIREBASE_CONSULTATION_STATUS_ASSIGNED:
                                view.showPatientActions();

                                break;
                        }

                        break;

                    default:
                        Logger.e("UserType is not set");

                        break;
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
            sendMessageUseCase.execute(consultationId, genericMessage);

            // Clear input view
            view.clearInput();

            // Handle each type of User
            switch (currentUserType) {
                case Constants.FIREBASE_USER_TYPE_MEDIC:
                    // Send notification to Patient
                    createNewMessageTaskUseCase.execute(consultationId, patientId, "Mensaje enviado por: " + currentUserName);

                    break;

                case Constants.FIREBASE_USER_TYPE_PATIENT:
                    // Send notification to Medic
                    createNewMessageTaskUseCase.execute(consultationId, medicId, "Mensaje enviado por: " + currentUserName);

                    break;
            }

            if (BuildConfig.DEBUG) {
                Logger.d("New Message sent successfully");
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

        this.stopConsultationListener();

        // Update Consultation status
        closeConsultationUseCase.execute(consultationId, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                // Create Task
                createConsultationClosedTaskUseCase.execute(consultationId, medicId, patientId);

                // Update View
                view.hideLoading();
                view.onClose();

                if (BuildConfig.DEBUG) {
                    Logger.d("Consultation Closed data saved successfully.");
                }
            }
        });
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
