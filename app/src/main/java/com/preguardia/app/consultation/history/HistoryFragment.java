package com.preguardia.app.consultation.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.consultation.details.ConsultationDetailsActivity;
import com.preguardia.app.consultation.model.GenericMessage;
import com.preguardia.app.general.Constants;

import net.grandcentrix.tray.TrayAppPreferences;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * @author amouly on 2/20/16.
 */
public class HistoryFragment extends Fragment implements HistoryContract.View {

    @Bind(R.id.consultation_history_list)
    RecyclerView recyclerView;

    private HistoryContract.UserActionsListener mActionListener;
    private MaterialDialog progressDialog;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(getActivity())
                .title(R.string.drawer_consultation_history)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        this.mActionListener = new HistoryPresenter(new Firebase(Constants.FIREBASE_URL_CONSULTATIONS),
                new TrayAppPreferences(getContext()), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation_history, container, false);

        ButterKnife.bind(this, view);



        return view;
    }

    @SuppressWarnings("unused")
    @OnItemClick(R.id.consultation_history_list)
    public void onItemClick() {
        Intent intent = new Intent(getActivity(), ConsultationDetailsActivity.class);

        startActivity(intent);
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
    public void showItems(List<GenericMessage> notes) {

    }
}