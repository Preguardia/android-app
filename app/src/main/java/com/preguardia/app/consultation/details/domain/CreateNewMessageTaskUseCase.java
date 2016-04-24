package com.preguardia.app.consultation.details.domain;

import com.preguardia.app.data.Repository;

/**
 * @author amouly on 4/24/16.
 */
public class CreateNewMessageTaskUseCase {

    private Repository repository;

    public CreateNewMessageTaskUseCase(Repository repository) {
        this.repository = repository;
    }

    public void execute(String consultationId, String userId, String content) {
        repository.createNewMessageTask(consultationId, userId, content);
    }
}
