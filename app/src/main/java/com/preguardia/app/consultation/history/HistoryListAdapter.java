package com.preguardia.app.consultation.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.consultation.model.Consultation;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author amouly on 3/12/16.
 */
public class HistoryListAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private List<Consultation> historyList;
    private HistoryContract.ConsultationItemListener itemListener;

    public HistoryListAdapter(List<Consultation> itemsList, HistoryContract.ConsultationItemListener clickListener) {
        this.historyList = itemsList;
        this.itemListener = clickListener;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View item = inflater.inflate(R.layout.item_history_list, parent, false);

        return new HistoryViewHolder(parent.getContext(), item);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final Consultation consultation = historyList.get(position);

        switch (consultation.getStatus()) {
            case "pending":

                // TODO: replace with resource string
                holder.setUserName("Pendiente de aprobación");
                holder.setStateIcoImageView(R.drawable.ic_access_time_24dp);

                break;

            case "assigned":

                holder.setUserName(consultation.getMedicName());
                holder.setStateIcoImageView(R.drawable.ic_chevron_right_24dp);
                holder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemListener.onConsultationClick(consultation.getId());
                    }
                });

                break;

            case "closed":

                holder.setUserName(consultation.getMedicName());
                holder.setStateIcoImageView(R.drawable.ic_close_24dp);

                break;
        }

        // TODO: replace with dynamic image
        holder.setUserImageView("http://media.graciasdoc.com/pictures/user_placeholder.png");
        holder.setSummaryText(consultation.getSummary());
    }

    public void addItem(Consultation item) {
        historyList.add(item);
    }

    private void setList(List<Consultation> consultations) {
        historyList = checkNotNull(consultations);
    }

    public void replaceData(List<Consultation> consultations) {
        setList(consultations);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
