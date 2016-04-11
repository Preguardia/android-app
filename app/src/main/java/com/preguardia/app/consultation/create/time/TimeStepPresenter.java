package com.preguardia.app.consultation.create.time;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public class TimeStepPresenter implements TimeStepContract.Presenter {

    private final TimeStepContract.View view;

    public TimeStepPresenter(TimeStepContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItems() {
        List<TimeItem> items = new ArrayList<>();

        items.add(new TimeItem("Hoy"));
        items.add(new TimeItem("Ayer"));
        items.add(new TimeItem("La semana pasada"));
        items.add(new TimeItem("El mes pasado"));
        items.add(new TimeItem("El año pasado"));
        items.add(new TimeItem("Hace más de un año"));

        view.showItems(items);
    }
}
