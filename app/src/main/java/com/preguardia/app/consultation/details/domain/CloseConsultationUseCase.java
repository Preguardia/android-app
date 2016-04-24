package com.preguardia.app.consultation.details.domain;

import com.firebase.client.Firebase;
import com.preguardia.app.data.Repository;

/**
 * @author amouly on 4/24/16.
 */
public class CloseConsultationUseCase {

    private Repository repository;

    public CloseConsultationUseCase(Repository repository) {
        this.repository = repository;
    }

    public void execute(String consultationId, Firebase.CompletionListener completionListener) {
        repository.closeConsultationById(consultationId, completionListener);
    }
}
