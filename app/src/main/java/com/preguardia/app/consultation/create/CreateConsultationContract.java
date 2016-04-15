package com.preguardia.app.consultation.create;

import android.support.annotation.StringRes;

import java.io.IOException;

/**
 * @author amouly on 3/9/16.
 */
public interface CreateConsultationContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showSuccess();

        void showErrorMessage(@StringRes int message);
    }

    interface Presenter {

        void completeRequest();

        void takePicture() throws IOException;

        void saveConsultation(String category, String summary, String details);
    }
}
