package com.preguardia.app.wizard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.preguardia.app.R;
import com.preguardia.app.user.UserActivity;

public class WizardActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(SlideFragment.newInstance(R.layout.fragment_first_slide));
        addSlide(SlideFragment.newInstance(R.layout.fragment_second_slide));
        addSlide(SlideFragment.newInstance(R.layout.fragment_third_slide));

        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        //showSkipButton(true);
        //setProgressButtonEnabled(false);
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }
}
