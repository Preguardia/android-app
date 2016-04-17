package com.preguardia.app.consultation.create.conditions;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public class ConditionsStepPresenter implements ConditionsStepContract.Presenter {

    private final Resources resources;
    private ConditionsStepContract.View view;

    public ConditionsStepPresenter(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void loadItems() {
        List<ConditionItem> items = new ArrayList<>();

        items.add(new ConditionItem("Cancer", false));
        items.add(new ConditionItem("Diabetes", false));
        items.add(new ConditionItem("Enfermedades cardiacas", false));
        items.add(new ConditionItem("Presión alta", false));
        items.add(new ConditionItem("Colesterol alto", false));
        items.add(new ConditionItem("Asma", false));

        view.showItems(items);
    }

    @Override
    public void attachView(ConditionsStepContract.View view) {
        this.view = view;
    }
}
