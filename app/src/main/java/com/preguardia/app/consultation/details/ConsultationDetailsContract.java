package com.preguardia.app.consultation.details;

import android.support.annotation.StringRes;

import com.preguardia.app.data.model.GenericMessage;

import java.io.IOException;

/**
 * @author amouly on 3/11/16.
 */
public interface ConsultationDetailsContract {

    interface View {

        void configureAdapter(String userType);

        void showLoading();

        void hideLoading();

        void showSuccess();

        void showEmptyFieldError();

        void showImagePreview();

        void clearInput();

        void toggleKeyboard();

        void showUserName(String name);

        void showUserDesc(String desc);

        void addItem(GenericMessage item);

        void showMedicActions();

        void showPatientActions();

        void onCloseConsultationClick();

        void onAttachFileClick();

        void onClose();

        void dismissNewMessageNotification();

        void invalidateMessageInput();

        void invalidateActions();

        void showRating();

        void hideRating();

        void showMessage(@StringRes int res);
    }

    interface Presenter {

        void init(String consultationId);

        void loadConsultation();

        void takePicture() throws IOException;

        void sendMessage(String message);

        void saveRating(float score, String comment);

        void sendPicture();

        void loadMessages();

        void closeConsultation();

        void stopConsultationListener();

        void stopMessagesListener();

        void attachView(ConsultationDetailsContract.View view);
    }
}
