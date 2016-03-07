package com.preguardia.app.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.batch.android.Batch;
import com.preguardia.app.R;
import com.preguardia.app.main.MainActivity;
import com.preguardia.app.user.login.LoginFragment;
import com.preguardia.app.user.register.RegisterActivity;

public class UserActivity extends AppCompatActivity {

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

    protected void onStart() {
        super.onStart();

        Batch.onStart(this);
    }

    @Override
    protected void onStop() {
        Batch.onStop(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Batch.onDestroy(this);

        super.onDestroy();
    }

    public void onLoadRegisterSection() {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Batch.onNewIntent(this, intent);

        super.onNewIntent(intent);
    }

    public void onLoadConsultationMain() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
