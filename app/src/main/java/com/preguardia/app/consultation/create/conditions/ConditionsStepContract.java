package com.preguardia.app.consultation.create.conditions;

import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public interface ConditionsStepContract {

    interface View {
        void showItems(List<ConditionItem> items);
    }

    interface Presenter {
        void loadItems();

        void attachView(ConditionsStepContract.View view);
    }
}
