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

/**
 * @author amouly on 3/11/16.
 */
public class ConsultationDetailsPresenter implements ConsultationDetailsContract.Presenter {

    @NonNull
    private final ConsultationDetailsContract.View detailsView;
    @NonNull
    private Firebase consultationRef;
    @NonNull
    private Firebase messagesRef;
    @NonNull
    private final TrayAppPreferences appPreferences;

    private final String consultationId;
    private final String currentUserType;
    private ValueEventListener consultationListener;
    private ChildEventListener messagesListener;

    public ConsultationDetailsPresenter(@NonNull Firebase firebase,
                                        @NonNull TrayAppPreferences appPreferences,
                                        @NonNull ConsultationDetailsContract.View view,
                                        @NonNull String consultationId) {
        this.appPreferences = appPreferences;
        this.detailsView = view;
        this.consultationId = consultationId;

        this.messagesRef = firebase.child(Constants.FIREBASE_MESSAGES).child(this.consultationId);
        this.consultationRef = firebase.child(Constants.FIREBASE_CONSULTATIONS).child(this.consultationId);

        this.currentUserType = appPreferences.getString(Constants.PREFERENCES_USER_TYPE, null);
    }

    @Override
    public void sendMessage(String message) {
        if (!message.isEmpty()) {
            final GenericMessage genericMessage = new GenericMessage(message, "text", currentUserType);

            // Push message to Firebase with generated ID
            messagesRef.push().setValue(genericMessage);

            // Clear input view and hide keyboard
            detailsView.clearInput();
            detailsView.toggleKeyboard();
        }
    }

    @Override
    public void sendPicture() {

    }

    @Override
    public void loadItems() {
        //detailsView.showLoading();

        if (BuildConfig.DEBUG) {
            Logger.d("Load Consultation Messages - ID: " + consultationId);
        }

        detailsView.configureAdapter(currentUserType);

        // Listen to changes on Consultation
        consultationListener = consultationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Consultation consultation = dataSnapshot.getValue(Consultation.class);

                if (currentUserType.equals(Constants.FIREBASE_USER_TYPE_MEDIC)) {
                    final Patient patient = consultation.getPatient();

                    String patientName = patient.getName();
                    String patientMedical = patient.getMedical();

                    if ((patientName != null) && (patientMedical != null)) {
                        detailsView.showUserName(patientName);
                        detailsView.showUserDesc(patientMedical);
                    }
                } else {
                    final Medic medic = consultation.getMedic();

                    String medicName = medic.getName();
                    String medicPlate = medic.getPlate();

                    if ((medicName != null) && (medicPlate != null)) {
                        detailsView.showUserName(medicName);
                        detailsView.showUserDesc(medicPlate);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Handle new messages from Firebase
        messagesListener = messagesRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                // Convert Firebase object to POJO
                final GenericMessage model = snapshot.getValue(GenericMessage.class);

                // Add item to Messages list
                detailsView.addItem(model);
                //detailsView.hideLoading();

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
    public void stopListener() {
        consultationRef.removeEventListener(consultationListener);
        messagesRef.removeEventListener(messagesListener);
    }

    @Override
    public void takePicture() throws IOException {

    }
}
