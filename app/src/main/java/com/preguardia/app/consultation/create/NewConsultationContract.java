package com.preguardia.app.consultation.create;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * @author amouly on 3/9/16.
 */
public interface NewConsultationContract {

    interface View {

        void showLoading();

        void hideLoading();

        void setUserActionListener(UserActionsListener listener);

        void showSuccess(@Nullable String consultationId);

        void showEmptyFieldError();

        void showImagePreview(@NonNull String uri);

        void openDetails(@Nullable String consultationId);

        void openHistory();
    }

    interface UserActionsListener {

        void takePicture() throws IOException;

        void saveConsultation(String category, String summary, String details);
    }
}
