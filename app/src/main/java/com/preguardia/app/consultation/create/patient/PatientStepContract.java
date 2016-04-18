package com.preguardia.app.consultation.create.patient;

import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public interface PatientStepContract {

    interface View {
        void showItems(List<PatientItem> items);

        String getData();
    }

    interface Presenter {
        void loadItems();
    }
}
