package com.preguardia.app.consultation.create.symptoms;

import java.util.List;
import java.util.Map;

/**
 * @author amouly on 4/6/16.
 */
public interface SymptomsStepContract {

    interface View {
        void showItems(List<String> headers, Map<Integer, List<SymptomsItem>> items);
    }

    interface Presenter {

        void loadItems();

        void attachView(SymptomsStepContract.View view);
    }
}
