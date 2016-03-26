package com.preguardia.app.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.preguardia.app.R;
import com.preguardia.app.consultation.create.NewConsultationFragment;
import com.preguardia.app.consultation.history.HistoryFragment;
import com.preguardia.app.general.Constants;
import com.preguardia.app.general.HelpFragment;
import com.preguardia.app.general.TermsFragment;
import com.preguardia.app.notification.RegistrationIntentService;
import com.preguardia.app.user.profile.ProfileFragment;
import com.squareup.picasso.Picasso;

import net.grandcentrix.tray.TrayAppPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.nav_view) NavigationView navigationView;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private View drawerHeader;
    private ImageView userImageView;
    private TextView userNameTextView;
    private TextView userDescTextView;

    private MainPresenter presenter;
    private MaterialDialog progressDialog;

    private boolean isReceiverRegistered;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        configLoading();
        configDrawer();
        configDrawerHeader();

        presenter = new MainPresenter(new Firebase(Constants.FIREBASE_URL_USERS), new TrayAppPreferences(this), this);
        presenter.loadUserInfo();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences.getBoolean("sentTokenToServer", false);

                if (sentToken) {
                    System.out.println("GCM SEND MESSAGE");
                } else {
                    System.out.println("TOKEN ERROR MESSAGE");
                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void configLoading() {
        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();
    }

    private void configDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        drawerHeader = navigationView.getHeaderView(0);
    }

    private void configDrawerHeader() {
        userImageView = (ImageView) drawerHeader.findViewById(R.id.drawer_profile_image);
        userNameTextView = (TextView) drawerHeader.findViewById(R.id.drawer_profile_name);
        userDescTextView = (TextView) drawerHeader.findViewById(R.id.drawer_profile_desc);

        drawerHeader.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        String title = null;

        switch (id) {
            case R.id.nav_consultation_new:

                fragment = NewConsultationFragment.newInstance();
                title = getString(R.string.drawer_consultation_new);

                break;

            case R.id.nav_consultation_history:

                fragment = HistoryFragment.newInstance();
                title = getString(R.string.drawer_consultation_history);

                break;

            case R.id.nav_help:

                fragment = HelpFragment.newInstance();
                title = getString(R.string.drawer_help);

                break;

            case R.id.nav_terms:

                fragment = TermsFragment.newInstance();
                title = getString(R.string.drawer_terms);

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        this.loadSection(fragment, title);

        return true;
    }

    private void loadSection(Fragment fragment, String title) {
        setTitle(title);

        // Show Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    public void loadNewConsultationSection() {
        this.loadSection(NewConsultationFragment.newInstance(), getString(R.string.drawer_consultation_new));
        navigationView.setCheckedItem(R.id.nav_consultation_new);
    }

    @Override
    public void loadHistorySection() {
        this.loadSection(HistoryFragment.newInstance(), getString(R.string.drawer_consultation_history));
        navigationView.setCheckedItem(R.id.nav_consultation_history);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        // Load profile section
        loadSection(ProfileFragment.newInstance(), getString(R.string.drawer_user_profile));

        drawerLayout.closeDrawer(GravityCompat.START);
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
    public void showUserName(String name) {
        userNameTextView.setText(name);
    }

    @Override
    public void showUserDesc(String desc) {
        userDescTextView.setText(desc);
    }

    @Override
    public void showUserPicture(String url) {
        Picasso.with(this)
                .load(url)
                .into(userImageView);
    }

    @Override
    public void showMedicMenu() {
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_main_medic);
    }

    @Override
    public void showPatientMenu() {
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_main_patient);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter("registrationComplete"));
            isReceiverRegistered = true;
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {

                System.out.println("DEVICE NOT SUPPORTED");

                finish();
            }
            return false;
        }
        return true;
    }
}
