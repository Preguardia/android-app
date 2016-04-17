package com.preguardia.app.consultation.create.conditions;

import android.content.res.Resources;

import com.preguardia.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public class ConditionsStepPresenter implements ConditionsStepContract.Presenter {

    private final List<ConditionItem> items;
    private final String[] conditionsRes;
    private ConditionsStepContract.View view;

    public ConditionsStepPresenter(Resources resources) {
        this.items = new ArrayList<>();

        conditionsRes = resources.getStringArray(R.array.consultation_create_conditions);
    }

    @Override
    public void loadItems() {
        // Generate Conditions from Resources
        for (String condition : conditionsRes) {
            items.add(new ConditionItem(condition, false));
        }

        view.showItems(items);
    }

    @Override
    public void attachView(ConditionsStepContract.View view) {
        this.view = view;
    }
}
