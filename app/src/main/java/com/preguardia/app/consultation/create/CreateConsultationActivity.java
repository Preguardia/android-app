package com.preguardia.app.consultation.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.github.fcannizzaro.materialstepper.style.TabStepper;
import com.orhanobut.logger.Logger;
import com.preguardia.app.R;
import com.preguardia.app.consultation.create.allergies.AllergiesStepContract;
import com.preguardia.app.consultation.create.allergies.AllergiesStepFragment;
import com.preguardia.app.consultation.create.conditions.ConditionsStepContract;
import com.preguardia.app.consultation.create.conditions.ConditionsStepFragment;
import com.preguardia.app.consultation.create.description.DescriptionStepContract;
import com.preguardia.app.consultation.create.description.DescriptionStepFragment;
import com.preguardia.app.consultation.create.medications.MedicationsStepContract;
import com.preguardia.app.consultation.create.medications.MedicationsStepFragment;
import com.preguardia.app.consultation.create.patient.PatientStepFragment;
import com.preguardia.app.consultation.create.symptoms.SymptomsStepContract;
import com.preguardia.app.consultation.create.symptoms.SymptomsStepFragment;
import com.preguardia.app.consultation.create.time.TimeStepContract;
import com.preguardia.app.consultation.create.time.TimeStepFragment;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import java.util.List;

/**
 * @author amouly on 4/6/16.
 */
public class CreateConsultationActivity extends TabStepper implements CreateConsultationContract.View {

    private CreateConsultationContract.Presenter presenter;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Configure Stepper
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
        addStep(createFragment(new ConditionsStepFragment()));

        this.setupView();

        // Create and init presenter
        presenter = new CreateConsultationPresenter(new Firebase(Constants.FIREBASE_URL), new TrayAppPreferences(this));
        presenter.attachView(this);

        super.onCreate(savedInstanceState);

        // Retrieve sent Category
        String category = getIntent().getExtras().getString(Constants.EXTRA_CONSULTATION_CREATE_CATEGORY);

        Logger.i("Selected category: " + category);

        // Save sent Category
        presenter.saveCategory(category);

        // Not kill step fragments
        mPager.setOffscreenPageLimit(7);
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
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title(R.string.drawer_consultation_new)
                .content(R.string.consultation_create_cancel_title)
                .positiveText(R.string.consultation_create_cancel_positive)
                .negativeText(R.string.consultation_create_cancel_negative)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // Cancel Consultation
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
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
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onNext() {
        super.onNext();

        // Get current Fragment
        int pos = mPager.getCurrentItem();
        Fragment fragment = mPagerAdapter.getItem(pos);

        // Request content for each Fragment
        if (fragment instanceof PatientStepFragment) {
            System.out.println("PATIENT FRAGMENT");

            String selectedPatient = ((PatientStepFragment) fragment).getData();

            presenter.savePatient(selectedPatient);
        } else if (fragment instanceof DescriptionStepFragment) {
            System.out.println("DETAILS FRAGMENT");

            String inputText = ((DescriptionStepContract.View) fragment).getData();

            presenter.saveDescription(inputText);
        } else if (fragment instanceof TimeStepFragment) {
            System.out.println("TIME FRAGMENT");

            String selectedTime = ((TimeStepContract.View) fragment).getData();

            presenter.saveTime(selectedTime);
        } else if (fragment instanceof MedicationsStepFragment) {
            System.out.println("MEDICATIONS FRAGMENT");

            List<String> selectedMedications = ((MedicationsStepContract.View) fragment).getData();

            presenter.saveMedications(selectedMedications);
        } else if (fragment instanceof AllergiesStepFragment) {
            System.out.println("ALLERGIES FRAGMENT");

            List<String> selectedAllergies = ((AllergiesStepContract.View) fragment).getData();

            presenter.saveAllergies(selectedAllergies);
        } else if (fragment instanceof SymptomsStepFragment) {
            System.out.println("SYMPTOMS FRAGMENT");

            List<String> selectedSymptoms = ((SymptomsStepContract.View) fragment).getData();

            presenter.saveSymptoms(selectedSymptoms);
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();

        // Get current Fragment
        int pos = mPager.getCurrentItem();
        Fragment fragment = mPagerAdapter.getItem(pos);

        if (fragment instanceof ConditionsStepFragment) {
            System.out.println("CONDITIONS FRAGMENT");

            List<String> selectedConditions = ((ConditionsStepContract.View) fragment).getData();

            presenter.saveConditions(selectedConditions);
        }

        System.out.println("ON COMPLETE!");

        presenter.completeRequest();
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
                .cancelable(false)
                .positiveText(R.string.user_register_success_positive)
                .show();
    }

    @Override
    public void showErrorMessage(@StringRes int message) {
        Snackbar.make(getWindow().getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public CreateConsultationContract.Presenter getPresenter() {
        return presenter;
    }
}
