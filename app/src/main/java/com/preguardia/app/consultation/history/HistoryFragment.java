package com.preguardia.app.consultation.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 2/20/16.
 */
public class HistoryFragment extends Fragment {

    @Bind(R.id.consultation_history_list)
    ListView historyList;

    public static HistoryFragment newInstance(int param) {
        HistoryFragment sampleSlide = new HistoryFragment();

        Bundle args = new Bundle();
        args.putInt("PARAM", param);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation_history, container, false);

        ButterKnife.bind(this, view);


        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.item_history_list, R.id.item_history_medic);

        adapter.add("Ricardo Ramos - Pediatra");
        adapter.add("Marcos Gimenez - Oculista");

        historyList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}