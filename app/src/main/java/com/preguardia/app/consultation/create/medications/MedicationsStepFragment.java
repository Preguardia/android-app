package com.preguardia.app.consultation.create.medications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 4/6/16.
 */
public class MedicationsStepFragment extends AbstractStep implements MedicationsStepContract.View {

    @Bind(R.id.step_medications_container)
    LinearLayout itemsContainer;

    private MedicationsStepContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_medications, container, false);

        ButterKnife.bind(this, view);

        presenter = new MedicationsStepPresenter();
        presenter.attachView(this);
        presenter.loadMedications();

//        Spinner spinner = (Spinner) view.findViewById(R.id.item_medications_when);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.consultation_new_medications_how, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

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
        return mStepper.getString(R.string.consultation_new_step_medications);
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
    public void addItemView() {
        View view = View.inflate(getActivity(), R.layout.list_item_medications, itemsContainer);

    }

    @Override
    @OnClick(R.id.step_medications_add_button)
    public void onAddItemClick() {
        presenter.addItemListener();
    }
}