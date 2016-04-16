package com.preguardia.app.consultation.create.allergies;

/**
 * @author amouly on 4/6/16.
 */
public interface AllergiesStepContract {

    interface View {

        void addItemView();

        void onAddItemClick();
    }

    interface Presenter {

        void loadAllergies();

        void addAllergy(String allergy);

        void addItemListener();

        void attachView(AllergiesStepContract.View view);
    }
}
