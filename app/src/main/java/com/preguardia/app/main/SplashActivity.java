package com.preguardia.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.preguardia.app.general.Constants;
import com.preguardia.app.user.UserActivity;
import com.preguardia.app.wizard.WizardActivity;

import net.grandcentrix.tray.TrayAppPreferences;

/**
 * @author amouly on 3/6/16.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Initialize SharedPreferences
                TrayAppPreferences preferences = new TrayAppPreferences(getApplicationContext());

                // Create a new boolean and preference and set it to true
                boolean isFirstStart = preferences.getBoolean(Constants.PREFERENCES_FIRST_START, true);
                String userToken = preferences.getString(Constants.PREFERENCES_USER_TOKEN, null);

                // If the activity has never started before
                if (isFirstStart) {
                    // Launch app intro
                    Intent i = new Intent(SplashActivity.this, WizardActivity.class);
                    startActivity(i);

                    // Edit preference to make it false because we don't want this to run again
                    preferences.put(Constants.PREFERENCES_FIRST_START, false);
                } else if (userToken == null) {
                    // Launch User login
                    Intent i = new Intent(SplashActivity.this, UserActivity.class);
                    startActivity(i);
                } else {
                    // Launch app Main
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

        // Start the thread
        t.start();

        // Close Splash
        this.finish();
    }
}
