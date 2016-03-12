package com.preguardia.app.consultation.history;

import android.support.annotation.NonNull;

import com.firebase.client.Firebase;

import net.grandcentrix.tray.TrayAppPreferences;

/**
 * @author amouly on 3/10/16.
 */
public class HistoryPresenter implements HistoryContract.UserActionsListener {

    @NonNull
    private final HistoryContract.View historyView;
    @NonNull
    private final Firebase firebase;
    @NonNull
    final TrayAppPreferences appPreferences;

    public HistoryPresenter(@NonNull Firebase firebase,
                            @NonNull TrayAppPreferences appPreferences,
                            @NonNull HistoryContract.View view) {
        this.firebase = firebase;
        this.appPreferences = appPreferences;
        this.historyView = view;
    }

    @Override
    public void loadItems() {

    }
}
