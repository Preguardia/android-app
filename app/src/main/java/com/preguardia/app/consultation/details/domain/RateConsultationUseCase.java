package com.preguardia.app.consultation.details.domain;

import com.preguardia.app.data.Repository;
import com.preguardia.app.data.model.Rating;

/**
 * @author amouly on 4/25/16.
 */
public class RateConsultationUseCase {

    private Repository repository;

    public RateConsultationUseCase(Repository repository) {
        this.repository = repository;
    }

    public void execute(String consultationId, Rating rating) {
        repository.rateConsultationById(consultationId, rating);
    }
}
