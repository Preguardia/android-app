package com.preguardia.app.consultation.details;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.preguardia.app.MedicApp;
import com.preguardia.app.R;
import com.preguardia.app.data.model.GenericMessage;
import com.preguardia.app.general.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

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
    @Bind(R.id.consultation_details_bottom_action)
    ImageButton actionButton;

    @Inject
    ConsultationDetailsPresenter presenter;

    private MessagesListAdapter adapter;
    private MaterialDialog progressDialog;
    private MaterialDialog ratingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_consultation_details);
        ButterKnife.bind(this);

        // Get the requested Consultation ID
        String sentConsultation = getIntent().getStringExtra(Constants.EXTRA_CONSULTATION_ID);

        this.initDependencyInjector();
        this.setupView(sentConsultation);
    }

    private void setupView(String consultationId) {
        // Configure Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(this)
                .title(R.string.consultation_details_title)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        // Init Presenter
        presenter.attachView(this);
        presenter.init(consultationId);

        // Config Recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initDependencyInjector() {
        MedicApp application = (MedicApp) getApplication();
        DaggerConsultationDetailsComponent.builder()
                .applicationComponent(application.component())
                .consultationDetailsModule(new ConsultationDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.loadConsultation();
        presenter.loadMessages();
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.stopConsultationListener();
        presenter.stopMessagesListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.dismissNewMessageNotification();
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
        adapter = new MessagesListAdapter(this, userType, new ArrayList<GenericMessage>(0));
        recyclerView.setAdapter(adapter);
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
        toolbar.setTitle(name);
    }

    @Override
    public void showUserDesc(String desc) {
        toolbar.setSubtitle(desc);
    }

    @Override
    public void addItem(GenericMessage item) {
        adapter.addItem(item);

        int position = adapter.getItemCount() - 1;

        if (recyclerView != null) {
            recyclerView.scrollToPosition(position);
            adapter.notifyItemInserted(position);
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
    public void onAttachFileClick() {

    }

    @Override
    public void onClose() {
        finish();
    }

    @Override
    public void dismissNewMessageNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Constants.NOTIFICATION_NEW_MESSAGE_ID);
    }

    @Override
    public void invalidateMessageInput() {
        inputView.setFocusable(false);
        inputView.setHint(R.string.consultation_details_close_input_hint);
    }

    @Override
    public void invalidateActions() {
        actionButton.setVisibility(View.GONE);
    }

    @Override
    public void showRating() {
        ratingDialog = new MaterialDialog.Builder(this)
                .title(R.string.consultation_rating_title)
                .customView(R.layout.fragment_consultation_rating, true)
                .positiveText(R.string.consultation_rating_positive)
                .autoDismiss(false)
                .negativeText(R.string.consultation_rating_negative)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();

                        if (view != null) {
                            RatingBar ratingBar = ButterKnife.findById(view, R.id.consultation_rating_bar);
                            EditText editText = ButterKnife.findById(view, R.id.consultation_rating_comment);

                            float rating = ratingBar.getRating();
                            String comment = editText.getText().toString();

                            // Execute UseCase
                            presenter.saveRating(rating, comment);
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // Send empty Rating
                        presenter.saveRating(0, getString(R.string.consultation_rating_empty));

                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void hideRating() {
        ratingDialog.dismiss();
    }

    @Override
    public void showMessage(@StringRes int res) {
        Snackbar.make(toolbar, res, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.consultation_details_close:
                this.onCloseConsultationClick();

                return true;

            case R.id.consultation_details_attach:
                this.onAttachFileClick();

                return true;

            default:
                return false;
        }
    }
}
