package com.preguardia.app.consultation.history;

import android.support.annotation.Nullable;

import com.preguardia.app.data.model.Consultation;

import java.util.List;

/**
 * @author amouly on 3/10/16.
 */
public interface HistoryContract {

    interface View {

        void configAdapter(String userType);

        void showLoading();

        void hideLoading();

        void addItem(Consultation item);

        void showItemList(List<Consultation> items);

        void openDetails(@Nullable String consultationId);

        void showEmpty();

        void showResults();

        void hideEmpty();

        void hideResults();

        void showPendingMessage();
    }

    interface Presenter {

        void loadItems();

        void stopListener();

        void attachView(HistoryContract.View view);
    }

    interface ConsultationItemListener {
        void onConsultationClick(@Nullable String consultationId);
    }
}
