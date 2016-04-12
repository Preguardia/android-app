package com.preguardia.app.consultation.create.diseases;

import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public interface DiseasesStepContract {

    interface View {
        void showItems(List<DiseaseItem> items);
    }

    interface Presenter {
        void loadItems();
    }
}
