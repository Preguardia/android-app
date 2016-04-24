package com.preguardia.app.consultation.details;

import com.preguardia.app.data.Repository;
import com.preguardia.app.data.model.GenericMessage;

/**
 * @author amouly on 4/24/16.
 */
public class SendMessageUseCase {

    private Repository repository;

    public SendMessageUseCase(Repository repository) {
        this.repository = repository;
    }

    public void execute(String consultationId, GenericMessage genericMessage) {
        repository.sendMessage(consultationId, genericMessage);
    }
}
