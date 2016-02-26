package com.preguardia.app.user.register.medic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.user.UserActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 2/20/16.
 */
public class RegisterMedicFragment extends Fragment {


    public static RegisterMedicFragment newInstance(int param) {
        RegisterMedicFragment sampleSlide = new RegisterMedicFragment();

        Bundle args = new Bundle();
        args.putInt("PARAM", param);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_register_medic, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.register_medic_button)
    public void onRegisterClick() {
        ((UserActivity) getActivity()).onLoadConsultationMain();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
