package com.preguardia.app.consultation.history;

import com.preguardia.app.consultation.model.Consultation;

import java.util.List;

/**
 * @author amouly on 3/10/16.
 */
public interface HistoryContract {
    interface View {

        void showLoading();

        void hideLoading();

        void showItems(List<Consultation> consultations);

        void addItem(Consultation item);
    }

    interface UserActionsListener {

        void loadItems();
    }
}
