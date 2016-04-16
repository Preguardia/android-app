package com.preguardia.app.consultation.create.medications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.preguardia.app.R;

import java.util.ArrayList;
import java.util.List;

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
    private List<EditText> editTextList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_medications, container, false);

        ButterKnife.bind(this, view);

        presenter = new MedicationsStepPresenter();
        presenter.attachView(this);
        presenter.loadMedications();

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
        // For each EditText, store the value
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();

            if (!inputText.isEmpty()) {
                presenter.addMedication(inputText, null);
            }
        }

        return true;
    }

    @Override
    public String error() {
        return "<b>You must click!</b> <small>this is the condition!</small>";
    }

    @Override
    public void addItemView() {
        View view = View.inflate(getActivity(), R.layout.list_item_medication, itemsContainer);
        EditText input = ButterKnife.findById(view, R.id.item_medication_input);

        editTextList.add(input);
    }

    @Override
    @OnClick(R.id.step_medications_add_button)
    public void onAddItemClick() {
        presenter.addItemListener();
    }
}