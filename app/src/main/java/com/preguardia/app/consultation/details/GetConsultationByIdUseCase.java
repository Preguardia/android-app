package com.preguardia.app.consultation.details;

import com.firebase.client.ValueEventListener;
import com.preguardia.app.data.Repository;

/**
 * @author amouly on 4/24/16.
 */
public class GetConsultationByIdUseCase {

    private Repository repository;

    public GetConsultationByIdUseCase(Repository repository) {
        this.repository = repository;
    }

    public ValueEventListener execute(String consultationId, ValueEventListener valueEventListener) {
        return repository.getConsultationById(consultationId, valueEventListener);
    }

    public void stop(ValueEventListener valueEventListener) {
        repository.removeConsultationsEventListener(valueEventListener);
    }
}
