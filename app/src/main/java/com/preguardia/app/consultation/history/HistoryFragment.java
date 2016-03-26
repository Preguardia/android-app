package com.preguardia.app.consultation.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
import com.preguardia.app.consultation.details.ConsultationDetailsActivity;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.general.Constants;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import net.grandcentrix.tray.TrayAppPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 2/20/16.
 */
public class HistoryFragment extends Fragment implements HistoryContract.View {

    @Bind(R.id.consultation_history_list)
    RecyclerView recyclerView;

    @Bind(R.id.consultation_history_empty)
    RelativeLayout emptyView;
    @Bind(R.id.consultation_history_results)
    LinearLayout resultsView;

    private HistoryListAdapter mAdapter;
    private HistoryContract.Presenter presenter;
    private MaterialDialog progressDialog;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation_history, container, false);

        ButterKnife.bind(this, view);

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(getActivity())
                .title(R.string.drawer_consultation_history)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        presenter = new HistoryPresenter(new Firebase(Constants.FIREBASE_URL_CONSULTATIONS),
                new TrayAppPreferences(getContext()), this);

        // Config Recycler view
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.loadItems();
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.stopListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void configAdapter(String userType) {
        mAdapter = new HistoryListAdapter(new ArrayList<Consultation>(0), userType, mItemListener);
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
    public void addItem(Consultation item) {
        mAdapter.addItem(item);
        mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
    }

    @Override
    public void showItemList(List<Consultation> items) {
        mAdapter.replaceData(items);
    }

    @Override
    public void openDetails(@Nullable String consultationId) {
        Intent intent = new Intent(getActivity(), ConsultationDetailsActivity.class);

        intent.putExtra(Constants.EXTRA_CONSULTATION_ID, consultationId);

        startActivity(intent);
    }

    @Override
    public void showEmpty() {
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showResults() {
        resultsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void hideResults() {
        resultsView.setVisibility(View.GONE);
    }

    /**
     * Listener for clicks on notes in the RecyclerView.
     */
    HistoryContract.ConsultationItemListener mItemListener = new HistoryContract.ConsultationItemListener() {
        @Override
        public void onConsultationClick(@Nullable String consultationId) {
            openDetails(consultationId);
        }
    };
}