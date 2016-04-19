package com.preguardia.app.consultation.details;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
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
public class ConsultationDetailsActivity extends AppCompatActivity implements ConsultationDetailsContract.View,
        Toolbar.OnMenuItemClickListener {

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

        // Configure Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Get the requested Consultation ID
        String sentConsultation = getIntent().getStringExtra(Constants.EXTRA_CONSULTATION_ID);

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(this)
                .title(R.string.consultation_details_title)
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

        presenter.stopListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Constants.NOTIFICATION_NEW_MESSAGE_ID);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_details_bottom_action)
    public void onActionButtonClick() {
        String message = inputView.getText().toString();

        presenter.sendMessage(message);
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

    @Override
    public void showMedicActions() {
        toolbar.inflateMenu(R.menu.menu_consultation_details_medic);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void showPatientActions() {
        toolbar.inflateMenu(R.menu.menu_consultation_details_patient);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void onCloseConsultationClick() {
        new MaterialDialog.Builder(this)
                .title(R.string.consultation_details_title)
                .content(R.string.consultation_details_dialog_content)
                .positiveText(R.string.consultation_details_dialog_positive)
                .negativeText(R.string.consultation_details_dialog_negative)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // Cancel Consultation
                        presenter.closeConsultation();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onClose() {
        finish();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.consultation_details_close:
                this.onCloseConsultationClick();
                return true;

            default:
                return false;
        }
    }
}
