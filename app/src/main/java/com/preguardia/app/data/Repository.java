package com.preguardia.app.data;

import com.firebase.client.ChildEventListener;
import com.firebase.client.ValueEventListener;
import com.preguardia.app.data.model.GenericMessage;

/**
 * @author amouly on 3/14/16.
 */
public interface Repository {

    ValueEventListener getConsultationsByUser(String orderBy, String equalTo, ValueEventListener valueEventListener);

    ValueEventListener getConsultationById(String consultationId, ValueEventListener valueEventListener);

    void removeConsultationsEventListener(ValueEventListener eventListener);

    ChildEventListener getMessagesById(String consultationId, ChildEventListener childEventListener);

    void removeMessagesChildListener(ChildEventListener childEventListener);

    void sendMessage(String consultationId, GenericMessage genericMessage);
}
