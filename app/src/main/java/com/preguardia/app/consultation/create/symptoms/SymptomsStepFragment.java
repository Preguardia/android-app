package com.preguardia.app.consultation.create.symptoms;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.preguardia.app.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 4/6/16.
 */
public class SymptomsStepFragment extends AbstractStep implements SymptomsStepContract.View {

    @Bind(R.id.step_symptoms_list)
    RecyclerView recyclerView;

    private SymptomsStepContract.Presenter presenter;
    private SymptomsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_symptoms, container, false);

        ButterKnife.bind(this, view);

        presenter = new SymptomsStepPresenter(getResources());
        presenter.attachView(this);

        adapter = new SymptomsAdapter(new ArrayList<String>(0), new ArrayMap<Integer, List<SymptomsItem>>(0));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerView.setAdapter(adapter);

        presenter.loadItems();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStepVisible() {
        super.onStepVisible();
    }

    @Override
    public String name() {
        return mStepper.getString(R.string.consultation_new_step_symptoms);
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public boolean nextIf() {
        return true;
    }

    @Override
    public String error() {
        return "<b>You must click!</b> <small>this is the condition!</small>";
    }

    @Override
    public void showItems(List<String> headers, Map<Integer, List<SymptomsItem>> items) {
        adapter.replaceData(headers, items);
    }
}