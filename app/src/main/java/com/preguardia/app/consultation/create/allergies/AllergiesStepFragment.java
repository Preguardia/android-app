package com.preguardia.app.consultation.create.allergies;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

    private AllergiesStepContract.Presenter presenter;
    private List<EditText> editTextList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_allergies, container, false);

        ButterKnife.bind(this, view);

        this.layoutInflater = inflater;

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
        return mStepper.getString(R.string.consultation_create_step_allergies);
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public boolean nextIf() {
        hideKeyboardFrom(getActivity(), itemsContainer);

        return true;
    }

    @Override
    public String error() {
        return null;
    }

    @Override
    public void addItemView() {
        EditText view = (EditText) layoutInflater.inflate(R.layout.list_item_allergy, itemsContainer, false);

        itemsContainer.addView(view);
        editTextList.add(view);
    }

    @Override
    @OnClick(R.id.step_allergies_add_button)
    public void onAddItemClick() {
        presenter.addItemListener();
    }

    @Override
    public List<String> getData() {
        // For each EditText, store the value
        for (EditText editText : editTextList) {
            String inputText = editText.getText().toString();

            if (!inputText.isEmpty()) {
                presenter.addAllergy(inputText);
            }
        }

        return presenter.getAllergies();
    }
}