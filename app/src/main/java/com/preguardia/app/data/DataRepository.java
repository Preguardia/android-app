package com.preguardia.app.data;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.preguardia.app.data.model.GenericMessage;
import com.preguardia.app.general.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author amouly on 3/7/16.
 */
public class DataRepository implements Repository {

    private final Firebase firebaseRef;
    private final Firebase consultationsRef;
    private final Firebase messagesRef;
    private final Firebase queueRef;

    public DataRepository(Firebase firebaseRef) {
        this.firebaseRef = firebaseRef;
        this.consultationsRef = firebaseRef.child(Constants.FIREBASE_CONSULTATIONS);
        this.messagesRef = firebaseRef.child(Constants.FIREBASE_MESSAGES);
        this.queueRef = firebaseRef.child(Constants.FIREBASE_QUEUE);
    }

    @Override
    public ValueEventListener getConsultationsByUser(String orderBy, String equalTo, ValueEventListener valueEventListener) {
        return consultationsRef
                .orderByChild(orderBy)
                .equalTo(equalTo)
                .addValueEventListener(valueEventListener);
    }

    @Override
    public ValueEventListener getConsultationById(String consultationId, ValueEventListener valueEventListener) {
        return consultationsRef
                .child(consultationId)
                .addValueEventListener(valueEventListener);
    }

    @Override
    public void closeConsultationById(String consultationId, Firebase.CompletionListener completionListener) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(Constants.FIREBASE_CONSULTATION_STATUS, Constants.FIREBASE_CONSULTATION_STATUS_CLOSED);

        consultationsRef
                .child(consultationId)
                .updateChildren(attributes, completionListener);
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

    @Override
    public void createNewMessageTask(String consultationId, String userId, String content) {
        // Create Task with data
        Map<String, String> task = new HashMap<>();
        task.put(Constants.FIREBASE_TASK_TYPE, Constants.FIREBASE_TASK_TYPE_MESSAGE_NEW);
        task.put(Constants.FIREBASE_TASK_CONTENT, content);
        task.put(Constants.FIREBASE_CONSULTATION_ID, consultationId);
        task.put(Constants.FIREBASE_USER_ID, userId);

        // Push task to be processed
        queueRef.child(Constants.FIREBASE_TASKS)
                .push()
                .setValue(task);
    }

    @Override
    public void createConsultationClosedTask(String consultationId, String medicId, String patientId) {
        // Create Task with data
        Map<String, String> task = new HashMap<>();
        task.put(Constants.FIREBASE_TASK_TYPE, Constants.FIREBASE_TASK_TYPE_CONSULTATION_CLOSED);
        task.put(Constants.FIREBASE_CONSULTATION_ID, consultationId);
        task.put(Constants.FIREBASE_MEDIC_ID, medicId);
        task.put(Constants.FIREBASE_PATIENT_ID, patientId);

        // Push task to be processed
        queueRef.child(Constants.FIREBASE_TASKS)
                .push()
                .setValue(task);
    }
}
