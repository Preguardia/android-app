package com.preguardia.app.consultation.details.domain;

import com.firebase.client.ChildEventListener;
import com.preguardia.app.data.Repository;

/**
 * @author amouly on 4/24/16.
 */
public class GetMessagesByIdUseCase {

    private Repository repository;

    public GetMessagesByIdUseCase(Repository repository) {
        this.repository = repository;
    }

    public ChildEventListener execute(String consultationId, ChildEventListener childEventListener) {
        return repository.getMessagesById(consultationId, childEventListener);
    }

    public void stop(ChildEventListener childEventListener) {
        repository.removeMessagesChildListener(childEventListener);
    }
}
