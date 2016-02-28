package com.preguardia.app.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.preguardia.app.R;
import com.preguardia.app.consultation.create.NewConsultationFragment;
import com.preguardia.app.consultation.history.HistoryFragment;
import com.preguardia.app.general.HelpFragment;
import com.preguardia.app.general.TermsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.nav_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            // Set default home
            navigationView.setCheckedItem(R.id.nav_consultation_new);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, NewConsultationFragment.newInstance(0))
                    .commit();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        String title = null;

        switch (id) {
            case R.id.nav_consultation_new:

                fragment = NewConsultationFragment.newInstance(0);
                title = getString(R.string.drawer_consultation_new);

                break;

            case R.id.nav_consultation_history:

                fragment = HistoryFragment.newInstance(0);
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

        setTitle(title);

        // Show section
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
