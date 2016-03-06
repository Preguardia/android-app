package com.preguardia.app.user.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.general.Constants;
import com.preguardia.app.user.UserActivity;

import net.grandcentrix.tray.TrayAppPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 2/20/16.
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    @Bind(R.id.user_login_email)
    TextInputLayout emailInput;
    @Bind(R.id.user_login_password)
    TextInputLayout passwordInput;

    private LoginContract.UserActionsListener mActionListener;
    private MaterialDialog progressDialog;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActionListener = new LoginPresenter(new Firebase(Constants.FIREBASE_URL), new TrayAppPreferences(getContext()), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);

        ButterKnife.bind(this, view);

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(getActivity())
                .title(R.string.user_login_title)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        return view;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.user_login_button)
    public void onLoginButtonClick() {
        final String email = emailInput.getEditText().getText().toString();
        final String password = passwordInput.getEditText().getText().toString();

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailInput.getWindowToken(), 0);

        mActionListener.loginUser(email, password);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.user_login_register_button)
    public void onRegisterButtonClick() {
        mActionListener.registerUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showLoginError(String message) {
        Snackbar.make(emailInput, getString(R.string.user_login_error_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyFieldError() {
        Snackbar.make(emailInput, getString(R.string.user_login_error_field), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void openMain() {
        ((UserActivity) getActivity()).onLoadConsultationMain();

        // Kill activity
        getActivity().finish();
    }

    @Override
    public void openRegister() {
        ((UserActivity) getActivity()).onLoadRegisterSection();
    }

    @Override
    public void setUserActionListener(LoginContract.UserActionsListener listener) {
        mActionListener = listener;
    }
}
