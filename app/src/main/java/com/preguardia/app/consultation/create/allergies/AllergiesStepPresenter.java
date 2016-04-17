package com.preguardia.app.consultation.create.allergies;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public class AllergiesStepPresenter implements AllergiesStepContract.Presenter {

    private AllergiesStepContract.View view;
    private List<String> allergies;

    public AllergiesStepPresenter() {
        this.allergies = new ArrayList<>();
    }

    @Override
    public void loadAllergies() {
        // Add dummy inputs
        view.addItemView();
        view.addItemView();
    }

    @Override
    public List<String> getAllergies() {
        return allergies;
    }

    @Override
    public void addAllergy(String allergy) {
        // Check if item already inserted
        if (!allergies.contains(allergy)) {
            allergies.add(allergy);
        }
    }

    @Override
    public void addItemListener() {
        view.addItemView();
    }

    @Override
    public void attachView(AllergiesStepContract.View view) {
        this.view = view;
    }
}
