package com.preguardia.app.consultation.create.description;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DescriptionStepFragment extends AbstractStep implements DescriptionStepContract.View {

    @Bind(R.id.step_description_input)
    EditText descriptionInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_description, container, false);

        ButterKnife.bind(this, view);

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
        return mStepper.getString(R.string.consultation_create_step_description);
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public boolean nextIf() {
        return !descriptionInput.getText().toString().isEmpty();
    }

    @Override
    public String error() {
        return mStepper.getString(R.string.consultation_create_step_description_error);
    }

    @Override
    public String getData() {
        String text = descriptionInput.getText().toString();

        return text;
    }
}
