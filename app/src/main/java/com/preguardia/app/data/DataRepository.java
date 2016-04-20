package com.preguardia.app.data;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.preguardia.app.general.Constants;

/**
 * @author amouly on 3/7/16.
 */
public class DataRepository implements Repository {

    private final Firebase firebaseRef;

    public DataRepository(Firebase firebaseRef) {
        this.firebaseRef = firebaseRef;
    }

    @Override
    public ValueEventListener getConsultations(String orderBy, String equalTo, ValueEventListener valueEventListener) {
        return firebaseRef
                .child(Constants.FIREBASE_CONSULTATIONS)
                .orderByChild(orderBy)
                .equalTo(equalTo)
                .addValueEventListener(valueEventListener);
    }
}
