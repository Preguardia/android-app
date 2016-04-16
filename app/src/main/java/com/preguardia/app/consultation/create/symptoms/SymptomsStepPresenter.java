package com.preguardia.app.consultation.create.symptoms;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author amouly on 4/6/16.
 */
public class SymptomsStepPresenter implements SymptomsStepContract.Presenter {

    private SymptomsStepContract.View view;

    public SymptomsStepPresenter() {
    }

    @Override
    public void loadItems() {
        List<String> headers = new ArrayList<>();
        Map<Integer, List<SymptomsItem>> items = new ArrayMap<>();

        headers.add("General");
        headers.add("Cabeza/Cuello");
        headers.add("Pecho");

        List<SymptomsItem> list1 = new ArrayList<>();

        list1.add(new SymptomsItem("Fiebre", false));
        list1.add(new SymptomsItem("Perdida de peso", false));
        list1.add(new SymptomsItem("Dificultades para dormir", false));
        list1.add(new SymptomsItem("Cambios de humor", false));
        list1.add(new SymptomsItem("Cansancio", false));
        list1.add(new SymptomsItem("Viajes recientes al exterior", false));
        list1.add(new SymptomsItem("Hospitalizado reciente", false));

        items.put(0, list1);

        List<SymptomsItem> list2 = new ArrayList<>();

        list2.add(new SymptomsItem("Item4", false));
        list2.add(new SymptomsItem("Item5", false));
        list2.add(new SymptomsItem("Item6", false));

        items.put(1, list2);

        List<SymptomsItem> list3 = new ArrayList<>();

        list3.add(new SymptomsItem("Item7", false));
        list3.add(new SymptomsItem("Item8", false));
        list3.add(new SymptomsItem("Item9", false));

        items.put(2, list3);

        view.showItems(headers, items);
    }

    @Override
    public void attachView(SymptomsStepContract.View view) {
        this.view = view;
    }

}
