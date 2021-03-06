package com.preguardia.app.consultation.create.conditions;

import android.os.Bundle;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 4/6/16.
 */
public class ConditionsStepFragment extends AbstractStep implements ConditionsStepContract.View {

    @Bind(R.id.step_diseases_list)
    RecyclerView recyclerView;

    private ConditionsStepContract.Presenter presenter;
    private ConditionsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_diseases, container, false);

        ButterKnife.bind(this, view);

        presenter = new ConditionsStepPresenter(getResources());
        presenter.attachView(this);

        adapter = new ConditionsAdapter(getActivity(), new ArrayList<ConditionItem>(0));

        // Config Recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public String name() {
        return mStepper.getString(R.string.consultation_create_step_conditions);
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
    public void showItems(List<ConditionItem> items) {
        adapter.replaceData(items);
    }

    @Override
    public List<String> getData() {
        return adapter.getSelectedItems();
    }
}