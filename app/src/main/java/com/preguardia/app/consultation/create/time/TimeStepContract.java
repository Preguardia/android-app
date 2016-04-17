package com.preguardia.app.consultation.create.time;

import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public interface TimeStepContract {

    interface View {
        void showItems(List<TimeItem> items);

        String getData();
    }

    interface Presenter {
        void loadItems();
    }
}
