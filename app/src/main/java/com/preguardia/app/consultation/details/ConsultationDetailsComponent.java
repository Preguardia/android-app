package com.preguardia.app.consultation.details;

import com.preguardia.app.consultation.details.domain.CloseConsultationUseCase;
import com.preguardia.app.consultation.details.domain.CreateConsultationClosedTaskUseCase;
import com.preguardia.app.consultation.details.domain.CreateNewMessageTaskUseCase;
import com.preguardia.app.consultation.details.domain.GetConsultationByIdUseCase;
import com.preguardia.app.consultation.details.domain.GetMessagesByIdUseCase;
import com.preguardia.app.consultation.details.domain.RateConsultationUseCase;
import com.preguardia.app.consultation.details.domain.SendMessageUseCase;
import com.preguardia.app.injection.ActivityScope;
import com.preguardia.app.injection.component.ApplicationComponent;

import dagger.Component;

/**
 * @author amouly on 4/24/16.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ConsultationDetailsModule.class})
public interface ConsultationDetailsComponent {

    void inject(ConsultationDetailsActivity consultationDetailsActivity);

    GetConsultationByIdUseCase getConsultationByIdUseCase();

    GetMessagesByIdUseCase getMessagesByIdUseCase();

    SendMessageUseCase sendMessageUseCase();

    CreateNewMessageTaskUseCase createNewMessageTaskUseCase();

    CloseConsultationUseCase closeConsultationUseCase();

    CreateConsultationClosedTaskUseCase createConsultationClosedTaskUseCase();

    RateConsultationUseCase rateConsultationUseCase();
}
