package com.preguardia.app.consultation.create;

import android.support.annotation.NonNull;

import java.io.IOException;

/**
 * @author amouly on 3/9/16.
 */
public interface NewConsultationContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showSuccess();

        void showEmptyFieldError();

        void showImagePreview(@NonNull String uri);

        void openHistory();

        void showErrorMessage(String message);
    }

    interface Presenter {

        void takePicture() throws IOException;

        void saveConsultation(String category, String summary, String details);
    }
}
