package com.preguardia.app.consultation.create.allergies;

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
public class AllergiesStepFragment extends AbstractStep implements AllergiesStepContract.View {

    @Bind(R.id.step_allergies_container)
    LinearLayout itemsContainer;
    AllergiesStepContract.Presenter presenter;
    private List<EditText> editTextList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_allergies, container, false);

        ButterKnife.bind(this, view);

        presenter = new AllergiesStepPresenter();
        presenter.attachView(this);
        presenter.loadAllergies();

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
        return mStepper.getString(R.string.consultation_new_step_allergies);
    }

    @Override
    public boolean isOptional() {
        return true;
    }

    @Override
    public boolean nextIf() {
        // For each EditText, store the value
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();

            if (!inputText.isEmpty()) {
                presenter.addAllergy(inputText);
            }
        }

        return true;
    }

    @Override
    public String error() {
        return null;
    }

    @Override
    public void addItemView() {
        View view = View.inflate(getActivity(), R.layout.list_item_allergies, itemsContainer);
        EditText input = ButterKnife.findById(view, R.id.item_allergy_input);

        editTextList.add(input);
    }

    @Override
    @OnClick(R.id.step_allergies_add_button)
    public void onAddItemClick() {
        presenter.addItemListener();
    }
}