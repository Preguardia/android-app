package com.preguardia.app.consultation.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.preguardia.app.MedicApp;
import com.preguardia.app.R;
import com.preguardia.app.consultation.details.ConsultationDetailsActivity;
import com.preguardia.app.data.model.Consultation;
import com.preguardia.app.general.Constants;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
    @Inject
    HistoryPresenter presenter;
    /**
     * Listener for clicks on notes in the RecyclerView.
     */
    HistoryContract.ConsultationItemListener mItemListener = new HistoryContract.ConsultationItemListener() {
        @Override
        public void onConsultationClick(@Nullable String consultationId) {
            // If null, it' pending
            if (consultationId != null) {
                openDetails(consultationId);
            } else {
                showPendingMessage();
            }
        }
    };
    private HistoryListAdapter mAdapter;
    private MaterialDialog progressDialog;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation_history, container, false);

        ButterKnife.bind(this, view);

        this.initDependencyInjector();
        this.setupView();

        return view;
    }

    private void setupView() {
        presenter.attachView(this);

        // Init Progress dialog
        MaterialDialog.Builder progressBuilder = new MaterialDialog.Builder(getActivity())
                .title(R.string.drawer_consultation_history)
                .content(R.string.user_login_loading)
                .cancelable(false)
                .progress(true, 0);

        progressDialog = progressBuilder.build();

        // Config Recycler view
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
    }

    private void initDependencyInjector() {
        MedicApp application = (MedicApp) getActivity().getApplication();
        DaggerHistoryComponent.builder()
                .applicationComponent(application.component())
                .historyModule(new HistoryModule(getContext()))
                .build()
                .inject(this);
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
        mAdapter = new HistoryListAdapter(getContext(), new ArrayList<Consultation>(0), userType, mItemListener);
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

    @Override
    public void showPendingMessage() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.drawer_consultation_history)
                .content(R.string.consultation_history_pending_message)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .positiveText(R.string.consultation_history_pending_button)
                .cancelable(false)
                .autoDismiss(true)
                .show();
    }
}