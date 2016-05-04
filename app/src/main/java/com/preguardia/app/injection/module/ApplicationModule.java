package com.preguardia.app.injection.module;

import com.firebase.client.Firebase;
import com.preguardia.app.MedicApp;
import com.preguardia.app.data.DataRepository;
import com.preguardia.app.data.Repository;
import com.preguardia.app.general.Constants;
import com.preguardia.app.user.SessionManager;

import net.grandcentrix.tray.TrayAppPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author amouly on 3/14/16.
 */
@Module
public class ApplicationModule {

    private final String deviceId;

    private MedicApp medicApp;

    public ApplicationModule(MedicApp application, String deviceId) {
        this.medicApp = application;
        this.deviceId = deviceId;
    }

    @Provides
    @Singleton
    MedicApp provideApplication() {
        return medicApp;
    }

    @Provides
    @Singleton
    Repository provideDataRepository(Firebase firebase) {
        return new DataRepository(firebase);
    }

    @Provides
    @Singleton
    Firebase provideFireBase() {
        return new Firebase(Constants.FIREBASE_URL);
    }

    @Provides
    @Singleton
    TrayAppPreferences provideAppPreferences(MedicApp application) {
        return new TrayAppPreferences(application);
    }

    @Provides
    @Singleton
    SessionManager provideSessionManager(TrayAppPreferences trayAppPreferences) {
        return new SessionManager(trayAppPreferences);
    }
}
