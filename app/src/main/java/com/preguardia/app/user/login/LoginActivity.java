package com.preguardia.app.user.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.general.Constants;
import com.preguardia.app.main.MainActivity;
import com.preguardia.app.user.register.RegisterActivity;

import net.grandcentrix.tray.TrayAppPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @Bind(R.id.user_login_email)
    TextInputLayout emailInput;
    @Bind(R.id.user_login_password)
    TextInputLayout passwordInput;

    private LoginContract.Presenter presenter;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(this)
                .title(R.string.user_login_title)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        presenter = new LoginPresenter(new Firebase(Constants.FIREBASE_URL), new TrayAppPreferences(this), this);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.user_login_button)
    public void onLoginButtonClick() {
        final String email = emailInput.getEditText().getText().toString();
        final String password = passwordInput.getEditText().getText().toString();

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailInput.getWindowToken(), 0);

        presenter.loginUser(email, password);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.user_login_register_button)
    public void onRegisterButtonClick() {
        presenter.registerUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        // Kill activity
        finish();
    }

    @Override
    public void openRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
    }
}
