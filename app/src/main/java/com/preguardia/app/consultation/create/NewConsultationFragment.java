package com.preguardia.app.consultation.create;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author amouly on 2/20/16.
 */
public class NewConsultationFragment extends Fragment {


    public static NewConsultationFragment newInstance(int param) {
        NewConsultationFragment fragment = new NewConsultationFragment();

        Bundle args = new Bundle();
        args.putInt("PARAM", param);
        fragment.setArguments(args);

        return fragment;
    }

}
