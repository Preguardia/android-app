package com.preguardia.app.user.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.preguardia.app.R;
import com.preguardia.app.general.Constants;
import com.preguardia.app.notification.PubSubHelper;
import com.preguardia.app.wizard.WizardActivity;

import net.grandcentrix.tray.TrayAppPreferences;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 2/29/16.
 */
public class ProfileFragment extends Fragment {

    private TrayAppPreferences appPreferences;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        appPreferences = new TrayAppPreferences(getActivity());

        return view;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.profile_logout)
    public void onLogoutClick() {

        new Thread(new Runnable() {
            public void run() {
                InstanceID instanceID = InstanceID.getInstance(getActivity());

                try {
                    String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    String userType = appPreferences.getString(Constants.PREFERENCES_USER_TYPE, null);

                    PubSubHelper pubSubHelper = new PubSubHelper(getActivity());

                    pubSubHelper.unsubscribeTopic(token, "/topics/" + userType);
                    pubSubHelper.unsubscribeTopic(token, "/topics/medic");
                    pubSubHelper.unsubscribeTopic(token, "/topics/patient");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        appPreferences.clear();

        // Restart app instance
        Intent intent = new Intent(getActivity(), WizardActivity.class);
        getActivity().startActivity(intent);

        // Close old app instance
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
