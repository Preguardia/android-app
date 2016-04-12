package com.preguardia.app.consultation.create.diseases;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public class DiseasesStepPresenter implements DiseasesStepContract.Presenter {

    private final DiseasesStepContract.View view;

    public DiseasesStepPresenter(DiseasesStepContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItems() {
        List<DiseaseItem> items = new ArrayList<>();

        items.add(new DiseaseItem("Cancer"));
        items.add(new DiseaseItem("Diabetes"));
        items.add(new DiseaseItem("Enfermedades cardiacas"));
        items.add(new DiseaseItem("Presión alta"));
        items.add(new DiseaseItem("Colesterol alto"));
        items.add(new DiseaseItem("Asma"));

        view.showItems(items);
    }
}
