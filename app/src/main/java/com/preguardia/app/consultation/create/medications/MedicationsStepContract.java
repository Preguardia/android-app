package com.preguardia.app.consultation.create.medications;

/**
 * @author amouly on 4/6/16.
 */
public interface MedicationsStepContract {

    interface View {

        void addItemView();

        void onAddItemClick();
    }

    interface Presenter {

        void loadMedications();

        void addMedication(String medication, String time);

        void addItemListener();

        void attachView(MedicationsStepContract.View view);
    }
}
