package com.preguardia.app;

import android.app.Application;
import android.provider.Settings;

import com.firebase.client.Firebase;
import com.orhanobut.logger.Logger;
import com.preguardia.app.injection.component.ApplicationComponent;
import com.preguardia.app.injection.component.DaggerApplicationComponent;
import com.preguardia.app.injection.module.ApplicationModule;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * @author amouly on 2/17/16.
 */
public class MedicApp extends Application {

    private ApplicationComponent component;
    private String deviceId;

    @Override
    public void onCreate() {
        super.onCreate();

        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        JodaTimeAndroid.init(this);

        // Init Logger
        Logger.init(BuildConfig.APPLICATION_ID);

        // Start Firebase
        Firebase.setAndroidContext(this);

        this.setupGraph();

        if (BuildConfig.DEBUG) {
            Logger.i("Application - Device ID: " + deviceId);
        }
    }

    private void setupGraph() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this, deviceId))
                .build();
        component.inject(this);
    }

    public ApplicationComponent component() {
        return component;
    }

}
