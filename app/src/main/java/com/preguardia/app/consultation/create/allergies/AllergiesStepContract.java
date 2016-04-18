package com.preguardia.app.consultation.create.allergies;

import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public interface AllergiesStepContract {

    interface View {

        void addItemView();

        void onAddItemClick();

        List<String> getData();
    }

    interface Presenter {

        void loadAllergies();

        List<String> getAllergies();

        void addAllergy(String allergy);

        void addItemListener();

        void attachView(AllergiesStepContract.View view);
    }
}
