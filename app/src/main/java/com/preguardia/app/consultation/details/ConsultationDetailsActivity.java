package com.preguardia.app.consultation.details;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.consultation.model.GenericMessage;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 2/27/16.
 */
public class ConsultationDetailsActivity extends AppCompatActivity implements ConsultationDetailsContract.View {

    @Bind(R.id.consultation_details_toolbar)
    Toolbar toolbar;
    @Bind(R.id.consultation_details_list)
    RecyclerView recyclerView;
    @Bind(R.id.consultation_details_bottom_input)
    TextView inputView;

    @Bind(R.id.consultation_details_user_name)
    TextView userNameView;
    @Bind(R.id.consultation_details_user_desc)
    TextView userDescView;

    private MessagesListAdapter mAdapter;
    private MaterialDialog progressDialog;
    private ConsultationDetailsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consultation_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Get the requested Consultation ID
        String sentConsultation = getIntent().getStringExtra(Constants.EXTRA_CONSULTATION_ID);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.consultation_details_title);
        }

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(this)
                .title(R.string.drawer_consultation_history)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        // Create presenter with sent ID
        presenter = new ConsultationDetailsPresenter(new Firebase(Constants.FIREBASE_URL),
                new TrayAppPreferences(this),
                this,
                sentConsultation);

        // Config Recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Load first items and attach
        presenter.loadItems();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_details_bottom_action)
    public void onActionButtonClick() {
        String message = inputView.getText().toString();

        presenter.sendMessage(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consultation_details_medic, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void configureAdapter(String userType) {
        // Create adapter with empty list
        mAdapter = new MessagesListAdapter(this, userType, new ArrayList<GenericMessage>(0));
        recyclerView.setAdapter(mAdapter);
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
    public void showSuccess() {

    }

    @Override
    public void showEmptyFieldError() {

    }

    @Override
    public void showImagePreview() {

    }

    @Override
    public void clearInput() {
        inputView.setText("");
    }

    @Override
    public void toggleKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
    }

    @Override
    public void showUserName(String name) {
        userNameView.setText(name);
    }

    @Override
    public void showUserDesc(String desc) {
        userDescView.setText(desc);
    }

    @Override
    public void addItem(GenericMessage item) {
        mAdapter.addItem(item);

        int position = mAdapter.getItemCount() - 1;

        if (recyclerView != null) {
            recyclerView.scrollToPosition(position);
            mAdapter.notifyItemInserted(position);
        }
    }
}
