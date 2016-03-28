package com.preguardia.app.wizard;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.preguardia.app.R;
import com.preguardia.app.user.login.LoginActivity;

public class WizardActivity extends AppIntro2 {

    @Override
    public void init(Bundle savedInstanceState) {
        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(SlideFragment.newInstance(R.layout.fragment_wizard_first));
        addSlide(SlideFragment.newInstance(R.layout.fragment_wizard_second));
        addSlide(SlideFragment.newInstance(R.layout.fragment_wizard_third));
        addSlide(SlideFragment.newInstance(R.layout.fragment_wizard_fourth));

        // Hide Skip/Done button.
        //showSkipButton(true);
        //setProgressButtonEnabled(false);
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();

        this.finish();
    }

    @Override
    public void onSlideChanged() {

    }

    public void loadMainActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
