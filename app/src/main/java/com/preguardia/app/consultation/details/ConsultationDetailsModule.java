package com.preguardia.app.consultation.details;

import android.content.Context;

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
}
