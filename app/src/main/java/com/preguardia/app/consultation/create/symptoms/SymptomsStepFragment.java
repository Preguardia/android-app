package com.preguardia.app.consultation.create.symptoms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.preguardia.app.R;

/**
 * @author amouly on 4/6/16.
 */
public class SymptomsStepFragment extends AbstractStep {

    private int i = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_step_symptoms, container, false);

        return v;
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
}