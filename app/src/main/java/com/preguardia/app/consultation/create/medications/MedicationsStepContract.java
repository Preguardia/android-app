package com.preguardia.app.consultation.create.medications;

import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public interface MedicationsStepContract {

    interface View {

        void addItemView();

        void onAddItemClick();

        List<String> getData();
    }

    interface Presenter {

        void loadMedications();

        void addMedication(String medication, String time);

        List<String> getMedications();

        void addItemListener();

        void attachView(MedicationsStepContract.View view);
    }
}
