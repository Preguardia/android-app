package com.preguardia.app.consultation.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.Firebase;
import com.preguardia.app.R;
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

    private HistoryListAdapter mAdapter;
    private HistoryContract.UserActionsListener mActionListener;
    private MaterialDialog progressDialog;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new HistoryListAdapter(new ArrayList<Consultation>(0));
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionListener.loadItems();
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

        // Config Recycler view
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());

        // Create adapter with empty list
        recyclerView.setAdapter(mAdapter);

        return view;
    }

//    @SuppressWarnings("unused")
//    @OnItemClick(R.id.consultation_history_list)
//    public void onItemClick() {
//        Intent intent = new Intent(getActivity(), ConsultationDetailsActivity.class);
//
//        startActivity(intent);
//    }

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
    public void showItems(List<Consultation> consultations) {

    }

    @Override
    public void addItem(Consultation item) {
        mAdapter.addItem(item);
        mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
    }
}