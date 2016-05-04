package com.preguardia.app.consultation.details.domain;

import com.preguardia.app.data.Repository;

/**
 * @author amouly on 4/24/16.
 */
public class CreateConsultationClosedTaskUseCase {

    private Repository repository;

    public CreateConsultationClosedTaskUseCase(Repository repository) {
        this.repository = repository;
    }

    public void execute(String consultationId, String medicId, String patientId) {
        repository.createConsultationClosedTask(consultationId, medicId, patientId);
    }
}
