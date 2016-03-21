package com.preguardia.app.consultation.approve;

/**
 * @author amouly on 3/17/16.
 */
public interface ApproveConsultationContract {

    interface View {

        void showPatientInfo(String name, String age, String medical, String pictureUrl);

        void showConsultationInfo(String category, String summary, String details);

        void onTakeButtonClick();

        void showMessage(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter {

        void loadConsultation();

        void takeConsultation();
    }
}
