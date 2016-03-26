package com.preguardia.app.main;

/**
 * @author amouly on 3/17/16.
 */
public interface MainContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showUserName(String name);

        void showUserDesc(String desc);

        void showUserPicture(String url);

        void showMedicMenu();

        void showPatientMenu();

        void loadNewConsultationSection();

        void loadHistorySection();
    }

    interface Presenter {

        void loadUserInfo();

    }
}
