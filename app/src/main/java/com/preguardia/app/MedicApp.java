package com.preguardia.app;

import android.app.Application;

import com.firebase.client.Firebase;
import com.orhanobut.logger.Logger;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * @author amouly on 2/17/16.
 */
public class MedicApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        // Init Logger
        Logger.init(BuildConfig.APPLICATION_ID);

        // Start Firebase
        Firebase.setAndroidContext(this);
    }

}
