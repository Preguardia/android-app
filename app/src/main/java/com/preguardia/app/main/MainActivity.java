package com.preguardia.app.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.batch.android.Batch;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.preguardia.app.BuildConfig;
import com.preguardia.app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.main_text);

        Firebase myFirebaseRef = new Firebase(BuildConfig.FIREBASE_API_URL);

        myFirebaseRef.child("consultations").setValue("Test value");

        myFirebaseRef.child("consultations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                textView.setText(snapshot.getValue(String.class));
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
}
