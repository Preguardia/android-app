package com.preguardia.app.consultation.create.patient;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 4/6/16.
 */
public class PatientStepFragment extends AbstractStep {

    private int i = 1;

    @Bind(R.id.step_patient_list)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_patient, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStepVisible() {
        super.onStepVisible();
        // do something
    }

    @Override
    public String name() {
        return "Tab " + getArguments().getInt("position", 0);
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public boolean nextIf() {
        return i > 1;
    }

    @Override
    public String error() {
        return "<b>You must click!</b> <small>this is the condition!</small>";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }
}