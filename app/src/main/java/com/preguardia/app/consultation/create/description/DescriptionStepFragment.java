package com.preguardia.app.consultation.create.description;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.preguardia.app.R;

public class DescriptionStepFragment extends AbstractStep {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_description, container, false);

        return v;
    }

    @Override
    public void onStepVisible() {
        super.onStepVisible();
    }

    @Override
    public String name() {
        return mStepper.getString(R.string.consultation_create_step_description);
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
}
