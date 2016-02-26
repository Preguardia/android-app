package com.preguardia.app.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.batch.android.Batch;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.R;
import com.preguardia.app.user.landing.LandingFragment;
import com.preguardia.app.user.register.medic.RegisterMedicFragment;
import com.preguardia.app.user.register.patient.RegisterPatientFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase myFirebaseRef = new Firebase(BuildConfig.FIREBASE_API_URL);

        myFirebaseRef.child("consultations").setValue("Test value");

        myFirebaseRef.child("consultations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });


        myFirebaseRef.authWithPassword("adrian@mouly.io", "", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());

                Batch.User.getEditor()
                        .setIdentifier(authData.getUid())
                        .save();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
            }
        });

        // Show Lading for User
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, LandingFragment.newInstance(0))
                .commit();
    }

    protected void onStart() {
        super.onStart();

        Batch.onStart(this);
    }

    @Override
    protected void onStop() {
        Batch.onStop(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Batch.onDestroy(this);

        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Batch.onNewIntent(this, intent);

        super.onNewIntent(intent);
    }

    public void onLoadRegisterPatient() {
        // Show Register section
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, RegisterPatientFragment.newInstance(0))
                .commit();
    }

    public void onLoadRegisterMedic() {
        // Show Register section
        getSupportFragmentManager()
                .beginTransaction()
                    .replace(R.id.main_container, RegisterMedicFragment.newInstance(0))
                .commit();
    }
}