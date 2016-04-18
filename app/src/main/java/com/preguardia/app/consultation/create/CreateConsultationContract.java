package com.preguardia.app.consultation.create;

import android.support.annotation.StringRes;

import java.util.List;

/**
 * @author amouly on 3/9/16.
 */
public interface CreateConsultationContract {

    interface View {
        void showLoading();

        void hideLoading();

        void showSuccess();

        void showErrorMessage(@StringRes int message);

        CreateConsultationContract.Presenter getPresenter();
    }

    interface Presenter {
        void saveCategory(String category);

        void savePatient(String patient);

        void saveDescription(String description);

        void saveTime(String time);

        void saveMedications(List<String> medications);

        void saveAllergies(List<String> allergies);

        void saveSymptoms(List<String> symptoms);

        void saveConditions(List<String> conditions);

        void completeRequest();

        void attachView(CreateConsultationContract.View view);
    }
}
