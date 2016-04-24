package com.preguardia.app.consultation.history;

import com.firebase.client.ValueEventListener;
import com.preguardia.app.data.Repository;

/**
 * @author amouly on 4/20/16.
 */
public class GetConsultationsUseCase {

    private Repository repository;

    public GetConsultationsUseCase(Repository repository) {
        this.repository = repository;
    }

    public ValueEventListener execute(String orderBy, String equalTo, ValueEventListener valueEventListener) {
        return repository.getConsultationsByUser(orderBy, equalTo, valueEventListener);
    }

    public void stop(ValueEventListener valueEventListener) {
        repository.removeConsultationsEventListener(valueEventListener);
    }
}
