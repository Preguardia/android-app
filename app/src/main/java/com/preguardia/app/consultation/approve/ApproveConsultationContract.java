package com.preguardia.app.consultation.approve;

import android.support.annotation.StringRes;

/**
 * @author amouly on 3/17/16.
 */
public interface ApproveConsultationContract {

    interface View {

        void showPatientInfo(String name, String age, String medical, String pictureUrl);

        void showCategory(String category);

        void showPatient(String patient);

        void showDescription(String description);

        void showFrequency(String frequency);

        void showMedications(String medications);

        void showAllergies(String allergies);

        void showSymptoms(String symptoms);

        void showConditions(String conditions);

        void onTakeButtonClick();

        void showMessage(@StringRes int message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter {

        void loadConsultation();

        void takeConsultation();

        void stopListener();
    }
}
