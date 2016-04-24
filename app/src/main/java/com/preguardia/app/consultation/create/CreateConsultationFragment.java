package com.preguardia.app.consultation.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.general.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 2/20/16.
 */
public class CreateConsultationFragment extends Fragment {

    public static CreateConsultationFragment newInstance() {
        return new CreateConsultationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation_create, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_create_undefined)
    public void onUndefinedClick() {
        openStepsActivity(Constants.FIREBASE_CONSULTATION_CATEGORY_UNDEFINED);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_create_clinics)
    public void onClinicsClick() {
        openStepsActivity(Constants.FIREBASE_CONSULTATION_CATEGORY_CLINICS);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_create_pediatrics)
    public void onPediatricsClick() {
        openStepsActivity(Constants.FIREBASE_CONSULTATION_CATEGORY_PEDIATRICS);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_create_dermatology)
    public void onDermatologyClick() {
        openStepsActivity(Constants.FIREBASE_CONSULTATION_CATEGORY_DERMATOLOGY);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_create_nutrition)
    public void onNutritionClick() {
        openStepsActivity(Constants.FIREBASE_CONSULTATION_CATEGORY_NUTRITION);
    }

    private void openStepsActivity(String category) {
        Intent intent = new Intent(getActivity(), CreateConsultationActivity.class);
        intent.putExtra(Constants.EXTRA_CONSULTATION_CREATE_CATEGORY, category);

        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }
}
