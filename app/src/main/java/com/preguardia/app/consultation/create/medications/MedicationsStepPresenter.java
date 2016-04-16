package com.preguardia.app.consultation.create.medications;

/**
 * @author amouly on 4/6/16.
 */
public class MedicationsStepPresenter implements MedicationsStepContract.Presenter {

    private MedicationsStepContract.View view;

    public MedicationsStepPresenter() {
    }

    @Override
    public void loadMedications() {
        view.addItemView();
        view.addItemView();
    }

    @Override
    public void addMedication(String medication, String time) {

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
