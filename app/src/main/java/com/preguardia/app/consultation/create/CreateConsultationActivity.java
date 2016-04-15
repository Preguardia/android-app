package com.preguardia.app.consultation.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.github.fcannizzaro.materialstepper.style.TabStepper;
import com.preguardia.app.R;
import com.preguardia.app.consultation.create.allergies.AllergiesStepFragment;
import com.preguardia.app.consultation.create.description.DescriptionStepFragment;
import com.preguardia.app.consultation.create.diseases.DiseasesStepFragment;
import com.preguardia.app.consultation.create.medications.MedicationsStepFragment;
import com.preguardia.app.consultation.create.patient.PatientStepFragment;
import com.preguardia.app.consultation.create.symptoms.SymptomsStepFragment;
import com.preguardia.app.consultation.create.time.TimeStepFragment;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

/**
 * @author amouly on 4/6/16.
 */
public class CreateConsultationActivity extends TabStepper implements CreateConsultationContract.View {

    CreateConsultationContract.Presenter presenter;
    private int i = 1;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setErrorTimeout(1500);
        setLinear(true);
        setTitle(getString(R.string.drawer_consultation_new));

        showPreviousButton();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        addStep(createFragment(new PatientStepFragment()));
        addStep(createFragment(new DescriptionStepFragment()));
        addStep(createFragment(new TimeStepFragment()));
        addStep(createFragment(new MedicationsStepFragment()));
        addStep(createFragment(new AllergiesStepFragment()));
        addStep(createFragment(new SymptomsStepFragment()));
        addStep(createFragment(new DiseasesStepFragment()));

        this.setupView();

        presenter = new CreateConsultationPresenter(new Firebase(Constants.FIREBASE_URL), new TrayAppPreferences(this), this);

        super.onCreate(savedInstanceState);
    }

    private void setupView() {
        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(this)
                .title(R.string.drawer_consultation_new)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private AbstractStep createFragment(AbstractStep fragment) {
        Bundle b = new Bundle();
        b.putInt("position", i++);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onComplete() {
        super.onComplete();

        this.showSuccess();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccess() {
        new MaterialDialog.Builder(this)
                .title(R.string.drawer_consultation_new)
                .content(R.string.consultation_new_success_message)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // Close Activity
                        finish();
                    }
                })
                .positiveText(R.string.user_register_success_positive)
                .show();
    }

    @Override
    public void showErrorMessage(@StringRes int message) {
        Snackbar.make(getWindow().getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
    }
}
