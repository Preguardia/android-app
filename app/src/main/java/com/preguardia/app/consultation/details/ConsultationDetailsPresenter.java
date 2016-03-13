package com.preguardia.app.consultation.details;

import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.orhanobut.logger.Logger;
import com.preguardia.app.consultation.model.GenericMessage;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import java.io.IOException;

/**
 * @author amouly on 3/11/16.
 */
public class ConsultationDetailsPresenter implements ConsultationDetailsContract.UserActionsListener {

    @NonNull
    private final ConsultationDetailsContract.View detailsView;
    @NonNull
    private Firebase consultationRef;
    @NonNull
    private Firebase messagesRef;
    @NonNull
    private final TrayAppPreferences appPreferences;
    private final String consultationId;

    public ConsultationDetailsPresenter(@NonNull Firebase firebase,
                                        @NonNull TrayAppPreferences appPreferences,
                                        @NonNull ConsultationDetailsContract.View view,
                                        @NonNull String consultationId) {
        this.appPreferences = appPreferences;
        this.detailsView = view;
        this.detailsView.setUserActionListener(this);
        this.consultationId = consultationId;
        this.messagesRef = firebase.child(Constants.FIREBASE_MESSAGES).child(this.consultationId);
        this.consultationRef = firebase.child(Constants.FIREBASE_CONSULTATIONS).child(this.consultationId);
    }

    @Override
    public void takePicture() throws IOException {

    }

    @Override
    public void sendMessage(String message) {
        if (!message.isEmpty()) {
            final GenericMessage genericMessage = new GenericMessage(message, "text", "patient");

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
        detailsView.showLoading();

        // Handle new messages from Firebase
        messagesRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                // Convert Firebase object to POJO
                final GenericMessage model = snapshot.getValue(GenericMessage.class);

                Logger.d("New message received: " + snapshot.getValue().toString());

                // Add item to Messages list
                detailsView.addItem(model);

                detailsView.hideLoading();
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
}
