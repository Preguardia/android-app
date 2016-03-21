package com.preguardia.app.consultation.approve;

import android.support.annotation.NonNull;

import com.firebase.client.Firebase;

import net.grandcentrix.tray.TrayAppPreferences;

/**
 * @author amouly on 3/17/16.
 */
public class ApproveConsultationPresenter implements ApproveConsultationContract.Presenter {

    @NonNull
    private final ApproveConsultationContract.View approveView;
    @NonNull
    private final Firebase firebase;
    @NonNull
    final TrayAppPreferences appPreferences;

    public ApproveConsultationPresenter(@NonNull Firebase firebase,
                                        @NonNull TrayAppPreferences appPreferences,
                                        @NonNull ApproveConsultationContract.View approveView) {
        this.firebase = firebase;
        this.appPreferences = appPreferences;
        this.approveView = approveView;
    }
}
