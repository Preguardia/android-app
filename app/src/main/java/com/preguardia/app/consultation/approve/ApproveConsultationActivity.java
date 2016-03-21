package com.preguardia.app.consultation.approve;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.general.Constants;
import com.squareup.picasso.Picasso;

import net.grandcentrix.tray.TrayAppPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 3/17/16.
 */
public class ApproveConsultationActivity extends AppCompatActivity implements ApproveConsultationContract.View {

    @Bind(R.id.consultation_approve_toolbar)
    Toolbar toolbar;

    @Bind(R.id.consultation_approve_patient_name)
    TextView patientNameTextView;
    @Bind(R.id.consultation_approve_patient_age)
    TextView patientAgeTextView;
    @Bind(R.id.consultation_approve_patient_medical)
    TextView patientMedicalTextView;
    @Bind(R.id.consultation_approve_patient_picture)
    ImageView patientImageView;

    private ApproveConsultationContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consultation_approve);

        // Get the requested Consultation ID
        String sentConsultation = getIntent().getStringExtra(Constants.EXTRA_CONSULTATION_ID);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.consultation_approve_title);
        }

        presenter = new ApproveConsultationPresenter(new Firebase(Constants.FIREBASE_URL_CONSULTATIONS),
                new TrayAppPreferences(this), this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    @Override
    public void showPatientInfo(String name, String age, String medical, String pictureUrl) {
        patientNameTextView.setText(name);
        patientAgeTextView.setText(age);
        patientMedicalTextView.setText(medical);

        Picasso.with(this)
                .load(pictureUrl)
                .into(patientImageView);
    }

    @Override
    public void showConsultationInfo(String category, String summary, String details) {

    }
}
