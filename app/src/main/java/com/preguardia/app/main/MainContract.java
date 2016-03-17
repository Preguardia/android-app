package com.preguardia.app.main;

/**
 * @author amouly on 3/17/16.
 */
public class MainContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showUserName(String name);

        void showUserDesc(String desc);

        void showUserPicture(String url);
    }

    interface Presenter {


    }
}
