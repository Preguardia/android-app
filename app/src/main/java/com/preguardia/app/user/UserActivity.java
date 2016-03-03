package com.preguardia.app.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.batch.android.Batch;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.consultation.model.Message;
import com.preguardia.app.general.Constants;
import com.preguardia.app.main.MainActivity;
import com.preguardia.app.user.login.LoginFragment;
import com.preguardia.app.user.register.RegisterFragment;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

//        myFirebaseRef.child("consultations").setValue("Test value");
//
//        myFirebaseRef.child("consultations").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                System.out.println(snapshot.getValue(String.class));
//            }
//
//            @Override
//            public void onCancelled(FirebaseError error) {
//            }
//        });


        ArrayList<Message> messages = new ArrayList<>();

        Message message1 = new Message("un texto1", "15/03/2014");
        Message message2 = new Message("un texto2", "15/03/2014");
        Message message3 = new Message("un texto3", "15/03/2014");
        Message message4 = new Message("un texto4", "15/03/2014");

        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        messages.add(message4);

        Consultation consultation = new Consultation("05/03/2014", "Un titulo", "Una categoria", "Un detalle", messages);

        Firebase listsRef = new Firebase(Constants.FIREBASE_URL_CONSULTATIONS);
        Firebase newListRef = listsRef.push();

            /* Save listsRef.push() to maintain same random Id */
        final String listId = newListRef.getKey();

        /**
         * Set raw version of date to the ServerValue.TIMESTAMP value and save into
         * timestampCreatedMap
         */

        /* Add the shopping list */
        newListRef.setValue(consultation);


//        myFirebaseRef.authWithPassword("adrian@mouly.io", "", new Firebase.AuthResultHandler() {
//            @Override
//            public void onAuthenticated(AuthData authData) {
//                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
//
//                Batch.User.getEditor()
//                        .setIdentifier(authData.getUid())
//                        .save();
//            }
//
//            @Override
//            public void onAuthenticationError(FirebaseError firebaseError) {
//                // there was an error
//            }
//        });

        // Show Lading for User
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, LoginFragment.newInstance(0))
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

    public void onLoadRegisterSection() {
        // Show Lading for User
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, RegisterFragment.newInstance(0))
                .commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Batch.onNewIntent(this, intent);

        super.onNewIntent(intent);
    }

    public void onLoadConsultationMain() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }


}
