package com.preguardia.app.data;

import com.firebase.client.ValueEventListener;

/**
 * @author amouly on 3/14/16.
 */
public interface Repository {

    ValueEventListener getConsultations(String orderBy, String equalTo, ValueEventListener valueEventListener);


}
