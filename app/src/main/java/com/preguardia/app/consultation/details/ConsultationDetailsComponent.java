package com.preguardia.app.consultation.details;

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
}
