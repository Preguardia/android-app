package com.preguardia.app.consultation.approve;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.general.Constants;
import com.squareup.picasso.Picasso;

import net.grandcentrix.tray.TrayAppPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Bind(R.id.consultation_approve_category_content)
    TextView categoryTextView;
    @Bind(R.id.consultation_approve_summary_content)
    TextView summaryTextView;
    @Bind(R.id.consultation_approve_details_content)
    TextView detailsTextView;

    private ApproveConsultationContract.Presenter presenter;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consultation_approve);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Get the requested Consultation ID
        String sentConsultation = getIntent().getStringExtra(Constants.EXTRA_CONSULTATION_ID);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.consultation_approve_title);
        }

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(this)
                .title(R.string.drawer_consultation_history)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        presenter = new ApproveConsultationPresenter(new Firebase(Constants.FIREBASE_URL_CONSULTATIONS),
                new TrayAppPreferences(this), this, sentConsultation);
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.loadConsultation();
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.stopListener();
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
        categoryTextView.setText(category);
        summaryTextView.setText(summary);
        detailsTextView.setText(details);
    }

    @Override
    @OnClick(R.id.consultation_approve_button)
    public void onTakeButtonClick() {
        presenter.takeConsultation();
    }

    @Override
    public void showMessage(String message) {
        new MaterialDialog.Builder(this)
                .title(R.string.consultation_approve_title)
                .content(message)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .positiveText("Ok")
                .autoDismiss(false)
                .show();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }
}
