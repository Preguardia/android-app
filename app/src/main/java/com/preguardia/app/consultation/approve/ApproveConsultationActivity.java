package com.preguardia.app.consultation.approve;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.preguardia.app.R;
import com.preguardia.app.general.Constants;

import butterknife.ButterKnife;

/**
 * @author amouly on 3/17/16.
 */
public class ApproveConsultationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consultation_details);

        // Get the requested Consultation ID
        String sentConsultation = getIntent().getStringExtra(Constants.EXTRA_CONSULTATION_ID);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }
}
