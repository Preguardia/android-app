package com.preguardia.app.consultation.create;

import android.os.Bundle;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.github.fcannizzaro.materialstepper.style.TabStepper;
import com.preguardia.app.R;
import com.preguardia.app.consultation.create.alergy.AlergyStepFragment;
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
        setTitle(R.string.drawer_consultation_new);
        setAlternativeTab(true);

        addStep(createFragment(new PatientStepFragment()));
        addStep(createFragment(new DescriptionStepFragment()));
        addStep(createFragment(new TimeStepFragment()));
        addStep(createFragment(new MedicationsStepFragment()));
        addStep(createFragment(new AlergyStepFragment()));
        addStep(createFragment(new SymptomsStepFragment()));
        addStep(createFragment(new DiseasesStepFragment()));

        super.onCreate(savedInstanceState);
    }

    private AbstractStep createFragment(AbstractStep fragment) {
        Bundle b = new Bundle();
        b.putInt("position", i++);
        fragment.setArguments(b);
        return fragment;
    }
}
