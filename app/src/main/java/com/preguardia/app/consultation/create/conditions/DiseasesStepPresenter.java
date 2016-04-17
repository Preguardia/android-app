package com.preguardia.app.consultation.create.conditions;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public class DiseasesStepPresenter implements DiseasesStepContract.Presenter {

    private final Resources resources;
    private DiseasesStepContract.View view;

    public DiseasesStepPresenter(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void loadItems() {
        List<DiseaseItem> items = new ArrayList<>();

        items.add(new DiseaseItem("Cancer", false));
        items.add(new DiseaseItem("Diabetes", false));
        items.add(new DiseaseItem("Enfermedades cardiacas", false));
        items.add(new DiseaseItem("Presión alta", false));
        items.add(new DiseaseItem("Colesterol alto", false));
        items.add(new DiseaseItem("Asma", false));

        view.showItems(items);
    }

    @Override
    public void attachView(DiseasesStepContract.View view) {
        this.view = view;
    }
}
