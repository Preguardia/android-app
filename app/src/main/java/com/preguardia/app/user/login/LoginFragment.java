package com.preguardia.app.user.login;

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
public class LoginFragment extends Fragment {

    public static LoginFragment newInstance(int param) {
        LoginFragment sampleSlide = new LoginFragment();

        Bundle args = new Bundle();
        args.putInt("PARAM", param);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.user_login_button)
    public void onLoginButtonClick() {
        ((UserActivity) getActivity()).onLoadConsultationMain();

        // Kill activity
        getActivity().finish();
    }

    @OnClick(R.id.user_login_register_button)
    public void onRegisterButtonClick() {
        ((UserActivity) getActivity()).onLoadRegisterSection();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
