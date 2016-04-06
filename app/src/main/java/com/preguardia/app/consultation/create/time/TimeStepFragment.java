package com.preguardia.app.consultation.create.time;

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

/**
 * @author amouly on 4/6/16.
 */
public class TimeStepFragment extends AbstractStep implements TimeStepContract.View {

    @Bind(R.id.step_time_list)
    RecyclerView recyclerView;

    private TimeStepContract.Presenter presenter;
    private TimeListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_time, container, false);

        presenter = new TimeStepPresenter(this);

        adapter = new TimeListAdapter(getActivity(), new ArrayList<TimeItem>(0));

        // Config Recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerView.setAdapter(adapter);

        presenter.loadItems();

        return view;
    }

    @Override
    public void onStepVisible() {
        super.onStepVisible();
    }

    @Override
    public String name() {
        return mStepper.getString(R.string.consultation_new_step_time);
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
    public void showItems(List<TimeItem> items) {
        adapter.replaceData(items);
    }
}
