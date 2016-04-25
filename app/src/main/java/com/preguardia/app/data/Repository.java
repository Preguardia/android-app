package com.preguardia.app.data;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.preguardia.app.data.model.GenericMessage;
import com.preguardia.app.data.model.Rating;

/**
 * @author amouly on 3/14/16.
 */
public interface Repository {

    ValueEventListener getConsultationsByUser(String orderBy, String equalTo, ValueEventListener valueEventListener);

    ValueEventListener getConsultationById(String consultationId, ValueEventListener valueEventListener);

    void closeConsultationById(String consultationId, Firebase.CompletionListener completionListener);

    void rateConsultationById(String consultationId, Rating rating);

    void removeConsultationsEventListener(ValueEventListener eventListener);

    ChildEventListener getMessagesById(String consultationId, ChildEventListener childEventListener);

    void removeMessagesChildListener(ChildEventListener childEventListener);

    void sendMessage(String consultationId, GenericMessage genericMessage);

    void createNewMessageTask(String consultationId, String userId, String content);

    void createConsultationClosedTask(String consultationId, String medicId, String patientId);
}
