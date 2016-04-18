package com.preguardia.app.consultation.create.patient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public class PatientStepPresenter implements PatientStepContract.Presenter {

    private final PatientStepContract.View view;

    public PatientStepPresenter(PatientStepContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItems() {
        List<PatientItem> items = new ArrayList<>();

        items.add(new PatientItem("Yo", false));
        items.add(new PatientItem("Mi hijo/a", false));
        items.add(new PatientItem("Otra persona", false));

        view.showItems(items);
    }
}
