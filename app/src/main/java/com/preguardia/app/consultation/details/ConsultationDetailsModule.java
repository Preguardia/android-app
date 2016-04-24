package com.preguardia.app.consultation.details;

import android.content.Context;

import com.preguardia.app.consultation.details.domain.CloseConsultationUseCase;
import com.preguardia.app.consultation.details.domain.CreateConsultationClosedTaskUseCase;
import com.preguardia.app.consultation.details.domain.CreateNewMessageTaskUseCase;
import com.preguardia.app.consultation.details.domain.GetConsultationByIdUseCase;
import com.preguardia.app.consultation.details.domain.GetMessagesByIdUseCase;
import com.preguardia.app.consultation.details.domain.SendMessageUseCase;
import com.preguardia.app.data.Repository;
import com.preguardia.app.injection.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author amouly on 4/24/16.
 */
@Module
public class ConsultationDetailsModule {

    private Context context;

    public ConsultationDetailsModule(Context context) {
        this.context = context;
    }

    @Provides
    @ActivityScope
    GetConsultationByIdUseCase provideGetConsultationByIdUseCase(Repository repository) {
        return new GetConsultationByIdUseCase(repository);
    }

    @Provides
    @ActivityScope
    GetMessagesByIdUseCase provideGetMessagesByIdUseCase(Repository repository) {
        return new GetMessagesByIdUseCase(repository);
    }

    @Provides
    @ActivityScope
    SendMessageUseCase provideSendMessageUseCase(Repository repository) {
        return new SendMessageUseCase(repository);
    }

    @Provides
    @ActivityScope
    CreateNewMessageTaskUseCase provideCreateNewMessageTaskUseCase(Repository repository) {
        return new CreateNewMessageTaskUseCase(repository);
    }

    @Provides
    @ActivityScope
    CloseConsultationUseCase provideCloseConsultationUseCase(Repository repository) {
        return new CloseConsultationUseCase(repository);
    }

    @Provides
    @ActivityScope
    CreateConsultationClosedTaskUseCase provideCreateConsultationClosedTaskUseCase(Repository repository) {
        return new CreateConsultationClosedTaskUseCase(repository);
    }
}
