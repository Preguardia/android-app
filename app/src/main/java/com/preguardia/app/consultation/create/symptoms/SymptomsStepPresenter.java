package com.preguardia.app.consultation.create.symptoms;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.util.ArrayMap;

import com.preguardia.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author amouly on 4/6/16.
 */
public class SymptomsStepPresenter implements SymptomsStepContract.Presenter {

    private SymptomsStepContract.View view;
    private Resources resources;

    public SymptomsStepPresenter(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void loadItems() {
        String[] headersArray = resources.getStringArray(R.array.consultation_create_symptoms_headers);
        List<String> headers = Arrays.asList(headersArray);

        Map<Integer, List<SymptomsItem>> items = new ArrayMap<>();

        // Auxiliary array of resources
        TypedArray ta = resources.obtainTypedArray(R.array.consultation_create_symptoms_list);

        // Request Array of item for each Header
        for (String head : headers) {
            int position = headers.indexOf(head);
            int arrayId = ta.getResourceId(position, 0);

            List<SymptomsItem> list = new ArrayList<>();

            if (arrayId > 0) {
                String[] resArray = resources.getStringArray(arrayId);

                for (String resItem : resArray) {
                    list.add(new SymptomsItem(resItem, false));
                }
            }

            items.put(position, list);
        }

        ta.recycle();

        view.showItems(headers, items);
    }

    @Override
    public void attachView(SymptomsStepContract.View view) {
        this.view = view;
    }

}
