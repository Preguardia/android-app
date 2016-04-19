/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.preguardia.app.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.preguardia.app.R;
import com.preguardia.app.consultation.approve.ApproveConsultationActivity;
import com.preguardia.app.consultation.details.ConsultationDetailsActivity;
import com.preguardia.app.general.Constants;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    private static final int MEDIC_REQUEST_CODE = 20;
    private static final int PATIENT_REQUEST_CODE = 30;
    private static final int GENERAL_NEW_MESSAGE_REQUEST_CODE = 10;

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String type = data.getString("type");
        String message = data.getString("message");
        String title = data.getString("title");
        String consultationId = data.getString(Constants.FIREBASE_CONSULTATION_ID);

        Log.d(TAG, "Type: " + type);
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Title: " + title);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG, "ConsultationId: " + consultationId);

        // Handle each Topic
        switch (from) {

            case "/topics/medic":

                // message received from some topic.
                showMedicNotification(title, message, consultationId);

                break;

            case "/topics/patient":

                // normal downstream message.
                showPatientNotification(title, message, consultationId);

                break;

            default:

                // Handle particular Notifications
                if (type != null) {
                    switch (type) {
                        case Constants.FIREBASE_TASK_TYPE_MESSAGE_NEW:

                            showMessageNewNotification(title, message, consultationId);

                            break;

                        case Constants.FIREBASE_TASK_TYPE_CONSULTATION_APPROVED:

                            showConsultationApprovedNotification(title, message, consultationId);

                            break;
                    }
                }

                break;
        }
    }

    private void showConsultationApprovedNotification(String title, String message, String consultationId) {
        // Prepare intent which is triggered if the notification is selected
        Intent intent = new Intent(this, ConsultationDetailsActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXTRA_CONSULTATION_ID, consultationId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, PATIENT_REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(4, notificationBuilder.build());
    }

    private void showMessageNewNotification(String title, String message, String consultationId) {
        // Prepare intent which is triggered if the notification is selected
        Intent intent = new Intent(this, ConsultationDetailsActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXTRA_CONSULTATION_ID, consultationId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, GENERAL_NEW_MESSAGE_REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }

    private void showMedicNotification(String title, String message, String consultationId) {
        // Prepare intent which is triggered if the notification is selected
        Intent intent = new Intent(this, ApproveConsultationActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXTRA_CONSULTATION_ID, consultationId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, MEDIC_REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(2, notificationBuilder.build());
    }

    private void showPatientNotification(String title, String message, String consultationId) {
        // Prepare intent which is triggered if the notification is selected
        Intent intent = new Intent(this, ConsultationDetailsActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXTRA_CONSULTATION_ID, consultationId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, PATIENT_REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(3, notificationBuilder.build());
    }
}
