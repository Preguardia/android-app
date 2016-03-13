package com.preguardia.app.consultation.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.consultation.details.ConsultationDetailsActivity;
import com.preguardia.app.general.Constants;
import com.preguardia.app.main.MainActivity;

import net.grandcentrix.tray.TrayAppPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author amouly on 2/20/16.
 */
public class NewConsultationFragment extends Fragment implements NewConsultationContract.View {

    @Bind(R.id.consultation_new_category_content)
    TextView categoryTextView;
    @Bind(R.id.consultation_new_where_content)
    TextView whereTextView;
    @Bind(R.id.consultation_new_lasttime_content)
    TextView lastTimeTextView;
    @Bind(R.id.consultation_new_summary_content)
    TextView summaryTextView;
    @Bind(R.id.consultation_new_details)
    EditText detailsEditText;

    private NewConsultationContract.UserActionsListener mActionListener;
    private MaterialDialog progressDialog;

    public NewConsultationFragment() {
    }

    public static NewConsultationFragment newInstance() {
        return new NewConsultationFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActionListener = new NewConsultationPresenter(new Firebase(Constants.FIREBASE_URL_CONSULTATIONS), new TrayAppPreferences(getContext()), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation_new, container, false);

        ButterKnife.bind(this, view);

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(getActivity())
                .title(R.string.drawer_consultation_new)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        return view;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_new_category)
    public void onCategoryClick() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.consultation_new_category)
                .items(R.array.consultation_category_list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        categoryTextView.setText(text);
                        categoryTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                    }
                })
                .show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_new_summary)
    public void onSummaryClick() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.consultation_new_summary_title)
                .content(R.string.consultation_new_summary_desc)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        summaryTextView.setText(input);
                        summaryTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                    }
                }).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_new_where)
    public void onWhereClick() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.consultation_new_where)
                .items(R.array.consultation_where_list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        whereTextView.setText(text);
                        whereTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                    }
                })
                .show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_new_lasttime)
    public void onLastTimeClick() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.consultation_new_lasttime)
                .items(R.array.consultation_lasttime_list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {


                        lastTimeTextView.setText(text);
                        lastTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                    }
                })
                .show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.consultation_new_submit)
    public void onSubmitClick() {
        String category = categoryTextView.getText().toString();
        String summary = summaryTextView.getText().toString();
        String details = detailsEditText.getText().toString();

        mActionListener.saveConsultation(category, summary, details);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
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
    public void setUserActionListener(NewConsultationContract.UserActionsListener listener) {
        this.mActionListener = listener;
    }

    @Override
    public void showEmptyFieldError() {

    }

    @Override
    public void showImagePreview(@NonNull String uri) {

    }

    @Override
    public void openDetails(@Nullable String consultationId) {
        Intent intent = new Intent(getActivity(), ConsultationDetailsActivity.class);

        intent.putExtra(Constants.EXTRA_CONSULTATION_ID, consultationId);

        startActivity(intent);
    }

    @Override
    public void openHistory() {
        ((MainActivity) getActivity()).loadHistorySection();
    }

    @Override
    public void showSuccess(@Nullable final String consultationId) {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.drawer_consultation_new)
                .content("Consulta creada exitosamente.")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        openHistory();
                    }
                })
                .positiveText(R.string.user_register_success_positive)
                .show();
    }
}
