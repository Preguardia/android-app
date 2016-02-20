package com.preguardia.app.user.landing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 2/20/16.
 */
public class LandingFragment extends Fragment {


    public static LandingFragment newInstance(int param) {
        LandingFragment sampleSlide = new LandingFragment();

        Bundle args = new Bundle();
        args.putInt("PARAM", param);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_landing, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.user_landing_patient)
    public void onPatientButtonClick() {
        ((MainActivity) getActivity()).onLoadPatientRegister();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
