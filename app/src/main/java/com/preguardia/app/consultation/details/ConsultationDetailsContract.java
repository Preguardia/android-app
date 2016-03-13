package com.preguardia.app.consultation.details;

import com.preguardia.app.consultation.model.GenericMessage;

import java.io.IOException;
import java.util.List;

/**
 * @author amouly on 3/11/16.
 */
public interface ConsultationDetailsContract {

    interface View {

        void showLoading();

        void hideLoading();

        void setUserActionListener(UserActionsListener listener);

        void showSuccess();

        void showEmptyFieldError();

        void showImagePreview();

        void clearInput();

        void toggleKeyboard();

        void showItems(List<GenericMessage> notes);

        void addItem(GenericMessage item);
    }

    interface UserActionsListener {

        void takePicture() throws IOException;

        void sendMessage(String message);

        void sendPicture();

        void loadItems();
    }
}
