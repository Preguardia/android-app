package com.preguardia.app.consultation.history;

import android.content.Context;

import com.preguardia.app.data.Repository;
import com.preguardia.app.injection.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author amouly on 4/20/16.
 */
@Module
public class HistoryModule {

    private Context context;

    public HistoryModule(Context context) {
        this.context = context;
    }

    @Provides
    @ActivityScope
    GetConsultationsUseCase provideGetConsultationsUseCase(Repository repository) {
        return new GetConsultationsUseCase(repository);
    }
}