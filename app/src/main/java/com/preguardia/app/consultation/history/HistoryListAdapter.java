package com.preguardia.app.consultation.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.consultation.model.Consultation;

import java.util.List;

/**
 * @author amouly on 3/12/16.
 */
public class HistoryListAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private List<Consultation> historyList;

    public HistoryListAdapter(List<Consultation> itemsList) {
        this.historyList = itemsList;
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

                holder.setUserName("Pendiente de aprobación");
                holder.setStateIcoImageView(R.drawable.ic_access_time_24dp);

                break;

            case "assigned":

                holder.setUserName(consultation.getMedicName());
                holder.setStateIcoImageView(R.drawable.ic_chevron_right_24dp);

                break;

            case "closed":

                holder.setUserName(consultation.getMedicName());
                holder.setStateIcoImageView(R.drawable.ic_close_24dp);

                break;
        }

        holder.setUserImageView("https://randomuser.me/api/portraits/med/men/54.jpg");
        holder.setSummaryText(consultation.getSummary());
    }

    public void addItem(Consultation item) {
        historyList.add(item);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
