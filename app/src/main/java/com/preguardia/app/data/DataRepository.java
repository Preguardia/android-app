package com.preguardia.app.data;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.preguardia.app.data.model.GenericMessage;
import com.preguardia.app.general.Constants;

/**
 * @author amouly on 3/7/16.
 */
public class DataRepository implements Repository {

    private final Firebase firebaseRef;
    private final Firebase consultationsRef;
    private final Firebase messagesRef;

    public DataRepository(Firebase firebaseRef) {
        this.firebaseRef = firebaseRef;
        this.consultationsRef = firebaseRef.child(Constants.FIREBASE_CONSULTATIONS);
        this.messagesRef = firebaseRef.child(Constants.FIREBASE_MESSAGES);
    }

    @Override
    public ValueEventListener getConsultationsByUser(String orderBy, String equalTo, ValueEventListener valueEventListener) {
        return consultationsRef
                .orderByChild(orderBy)
                .equalTo(equalTo)
                .addValueEventListener(valueEventListener);
    }

    @Override
    public ValueEventListener getConsultationById(String id, ValueEventListener valueEventListener) {
        return consultationsRef
                .child(id)
                .addValueEventListener(valueEventListener);
    }

    @Override
    public void removeConsultationsEventListener(ValueEventListener eventListener) {
        consultationsRef.removeEventListener(eventListener);
    }

    @Override
    public ChildEventListener getMessagesById(String consultationId, ChildEventListener childEventListener) {
        return messagesRef
                .child(consultationId)
                .addChildEventListener(childEventListener);
    }

    @Override
    public void removeMessagesChildListener(ChildEventListener childEventListener) {
        messagesRef.removeEventListener(childEventListener);
    }

    @Override
    public void sendMessage(String consultationId, GenericMessage genericMessage) {
        messagesRef
                .child(consultationId)
                .push()
                .setValue(genericMessage);
    }
}
