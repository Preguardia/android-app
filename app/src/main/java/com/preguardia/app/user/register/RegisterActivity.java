package com.preguardia.app.user.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author amouly on 2/20/16.
 */
public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.user_register_toolbar)
    Toolbar toolbar;
    @Bind(R.id.user_register_patient)
    Button patientButton;
    @Bind(R.id.user_register_medic)
    Button medicButton;

    @Bind(R.id.user_register_name)
    TextInputLayout nameInputView;
    @Bind(R.id.user_register_email)
    TextInputLayout emailImputView;
    @Bind(R.id.user_register_date)
    TextInputLayout dateImputView;
    @Bind(R.id.user_register_social)
    TextInputLayout socialInputView;
    @Bind(R.id.user_register_phone)
    TextInputLayout phoneInputView;
    @Bind(R.id.user_input_plate)
    TextInputLayout plateInputView;
    @Bind(R.id.user_register_plate_container)
    LinearLayout plateContainerView;
    @Bind(R.id.user_register_social_container)
    LinearLayout socialContainerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        patientButton.setPressed(true);
    }

    @OnTouch(R.id.user_register_patient)
    public boolean onPatientClick() {
        socialContainerView.setVisibility(View.VISIBLE);
        plateContainerView.setVisibility(View.GONE);

        patientButton.setPressed(true);
        medicButton.setPressed(false);

        return true;
    }

    @OnTouch(R.id.user_register_medic)
    public boolean onMedicClick() {
        socialContainerView.setVisibility(View.GONE);
        plateContainerView.setVisibility(View.VISIBLE);

        patientButton.setPressed(false);
        medicButton.setPressed(true);

        return true;
    }

    @OnClick(R.id.register_medic_button)
    public void onRegisterClick() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
