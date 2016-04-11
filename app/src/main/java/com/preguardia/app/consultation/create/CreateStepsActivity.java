package com.preguardia.app.consultation.create;

import android.os.Bundle;
import android.view.MenuItem;

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

/**
 * @author amouly on 4/6/16.
 */
public class CreateStepsActivity extends TabStepper {

    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setErrorTimeout(1500);
        setLinear(true);
        setTitle(getString(R.string.drawer_consultation_new));
        setAlternativeTab(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addStep(createFragment(new PatientStepFragment()));
        addStep(createFragment(new DescriptionStepFragment()));
        addStep(createFragment(new TimeStepFragment()));
        addStep(createFragment(new MedicationsStepFragment()));
        addStep(createFragment(new AllergiesStepFragment()));
        addStep(createFragment(new SymptomsStepFragment()));
        addStep(createFragment(new DiseasesStepFragment()));

        super.onCreate(savedInstanceState);
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
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}