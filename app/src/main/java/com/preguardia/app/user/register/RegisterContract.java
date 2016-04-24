package com.preguardia.app.user.register;

import android.support.annotation.StringRes;

/**
 * @author amouly on 3/5/16.
 */
public interface RegisterContract {

    interface View {

        void showSuccess();

        void showError(@StringRes int stringRes);

        void showProgress();

        void hideProgress();

        void openMain();

        void toggleKeyboard();

        void showSelectedBirthDate(String dateString);
    }

    interface Presenter {

        void registerUser(String type, String name, String email, String password, String password2, String birthDate,
                          String medical, String plate, String phone, boolean terms);
    }

}
