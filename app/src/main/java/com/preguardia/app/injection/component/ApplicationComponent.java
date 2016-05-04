package com.preguardia.app.injection.component;

import com.preguardia.app.MedicApp;
import com.preguardia.app.data.Repository;
import com.preguardia.app.injection.module.ApplicationModule;
import com.preguardia.app.user.SessionManager;

import net.grandcentrix.tray.TrayAppPreferences;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author amouly on 3/14/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MedicApp lecferApplication);

    MedicApp application();

    TrayAppPreferences appPreferences();

    SessionManager sessionManager();

    Repository repository();
}