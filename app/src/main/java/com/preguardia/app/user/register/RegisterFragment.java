package com.preguardia.app.user.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.preguardia.app.R;
import com.preguardia.app.user.UserActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author amouly on 2/20/16.
 */
public class RegisterFragment extends Fragment {

    @Bind(R.id.user_landing_patient)
    Button patientButton;
    @Bind(R.id.user_landing_medic)
    Button medicButton;

    @Bind(R.id.user_input_name)
    TextInputLayout nameInputView;
    @Bind(R.id.user_input_email)
    TextInputLayout emailImputView;
    @Bind(R.id.user_input_date)
    TextInputLayout dateImputView;
    @Bind(R.id.user_input_social)
    TextInputLayout socialInputView;
    @Bind(R.id.user_input_phone)
    TextInputLayout phoneImputView;
    @Bind(R.id.user_input_plate)
    TextInputLayout plateImputView;

    public static RegisterFragment newInstance(int param) {
        RegisterFragment sampleSlide = new RegisterFragment();

        Bundle args = new Bundle();
        args.putInt("PARAM", param);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_landing, container, false);

        ButterKnife.bind(this, view);

        patientButton.setPressed(true);

        return view;
    }

    @OnTouch(R.id.user_landing_patient)
    public boolean onPatientClick() {
        socialInputView.setVisibility(View.VISIBLE);
        plateImputView.setVisibility(View.GONE);

        patientButton.setPressed(true);
        medicButton.setPressed(false);

        return true;
    }

    @OnTouch(R.id.user_landing_medic)
    public boolean onMedicClick() {
        socialInputView.setVisibility(View.GONE);
        plateImputView.setVisibility(View.VISIBLE);

        patientButton.setPressed(false);
        medicButton.setPressed(true);

        return true;
    }

    @OnClick(R.id.register_medic_button)
    public void onRegisterClick() {
        ((UserActivity) getActivity()).onLoadConsultationMain();

        // Kill activity
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
