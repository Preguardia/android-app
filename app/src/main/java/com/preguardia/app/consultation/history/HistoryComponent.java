package com.preguardia.app.consultation.history;

import com.preguardia.app.injection.ActivityScope;
import com.preguardia.app.injection.component.ApplicationComponent;

import dagger.Component;

/**
 * @author amouly on 4/20/16.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {HistoryModule.class})
public interface HistoryComponent {

    void inject(HistoryFragment historyFragment);

    GetConsultationsUseCase getConsultationsUseCase();
}
