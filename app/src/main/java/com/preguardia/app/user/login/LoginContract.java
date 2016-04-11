package com.preguardia.app.user.login;

/**
 * @author amouly on 3/5/16.
 */
public interface LoginContract {

    interface View {

        void showLoginError(String message);

        void showEmptyFieldError();

        void showProgress();

        void hideProgress();

        void openMain();

        void openRegister();
    }

    interface Presenter {

        void loginUser(String email, String password);

        void registerUser();
    }
}