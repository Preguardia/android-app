package com.preguardia.app.consultation.create.medications;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public class MedicationsStepPresenter implements MedicationsStepContract.Presenter {

    private MedicationsStepContract.View view;
    private List<String> medications;

    public MedicationsStepPresenter() {
        this.medications = new ArrayList<>();
    }

    @Override
    public void loadMedications() {
        view.addItemView();
        view.addItemView();
    }

    @Override
    public void addMedication(String medication, String time) {
        // Check if item already inserted
        if (!medications.contains(medication)) {
            medications.add(medication);
        }
    }

    @Override
    public void addItemListener() {
        view.addItemView();
    }

    @Override
    public void attachView(MedicationsStepContract.View view) {
        this.view = view;
    }
}
