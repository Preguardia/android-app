package com.preguardia.app.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.preguardia.app.R;
import com.preguardia.app.main.MainActivity;
import com.preguardia.app.user.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Show login for User
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, LoginFragment.newInstance())
                .commit();
    }

    public void onLoadRegisterSection() {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
    }

    public void onLoadConsultationMain() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
