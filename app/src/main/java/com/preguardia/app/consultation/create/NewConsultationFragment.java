package com.preguardia.app.consultation.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.preguardia.app.R;

import butterknife.ButterKnife;

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation_new, container, false);

        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
