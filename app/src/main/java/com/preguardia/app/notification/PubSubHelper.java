package com.preguardia.app.notification;

/**
 * @author amouly on 3/27/16.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmPubSub;
import com.orhanobut.logger.Logger;

import java.io.IOException;

/**
 * This class used to subscribe and unsubscribe to topics.
 */
public class PubSubHelper {

    private final Context mContext;

    public PubSubHelper(Context context) {
        mContext = context;
    }

    /**
     * @param gcmToken the registration token obtained by registering
     * @param topic    the topic to subscribe to
     * @param extras   bundle with extra parameters
     */
    public void subscribeTopic(final String gcmToken, final String topic, final Bundle extras) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    GcmPubSub.getInstance(mContext).subscribe(gcmToken, topic, extras);

                    Logger.i("topic subscription succeeded."
                            + "\ngcmToken: " + gcmToken
                            + "\ntopic: " + topic
                            + "\nextras: " + extras);

                } catch (IOException | IllegalArgumentException e) {
                    Logger.i("topic subscription failed."
                            + "\nerror: " + e.getMessage()
                            + "\ngcmToken: " + gcmToken
                            + "\ntopic: " + topic
                            + "\nextras: " + extras);
                }
                return null;
            }
        }.execute();
    }

    /**
     * @param gcmToken the registration token obtained by registering
     * @param topic    the topic to unsubscribe from
     */
    public void unsubscribeTopic(final String gcmToken, final String topic) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    GcmPubSub.getInstance(mContext).unsubscribe(gcmToken, topic);
                    Logger.i("topic unsubscription succeeded."
                            + "\ngcmToken: " + gcmToken
                            + "\ntopic: " + topic);

                } catch (IOException | IllegalArgumentException e) {
                    Logger.i("topic unsubscription failed."
                            + "\nerror: " + e.getMessage()
                            + "\ngcmToken: " + gcmToken
                            + "\ntopic: " + topic);
                }

                return null;
            }
        }.execute();
    }
}
