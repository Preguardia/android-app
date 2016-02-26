package com.preguardia.app;

import android.app.Application;
import android.support.v4.content.ContextCompat;

import com.batch.android.Batch;
import com.batch.android.Config;
import com.firebase.client.Firebase;

/**
 * @author amouly on 2/17/16.
 */
public class MedicApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Start Firebase
        Firebase.setAndroidContext(this);

        // Start Batch
        Batch.Push.setGCMSenderId(BuildConfig.GCM_SENDER_ID);
        Batch.setConfig(new Config(BuildConfig.BATCH_API_KEY));
        Batch.Push.setNotificationsColor(ContextCompat.getColor(this, R.color.colorPrimary));
        Batch.Push.setSmallIconResourceId(R.mipmap.ic_launcher);
    }

}
