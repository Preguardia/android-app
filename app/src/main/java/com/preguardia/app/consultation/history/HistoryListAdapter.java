package com.preguardia.app.consultation.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.consultation.model.Consultation;
import com.preguardia.app.general.Constants;
import com.preguardia.app.user.model.Medic;
import com.preguardia.app.user.model.Patient;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author amouly on 3/12/16.
 */
public class HistoryListAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private final String userType;
    private List<Consultation> historyList;
    private HistoryContract.ConsultationItemListener itemListener;

    public HistoryListAdapter(List<Consultation> itemsList, String userType, HistoryContract.ConsultationItemListener clickListener) {
        this.historyList = itemsList;
        this.itemListener = clickListener;
        this.userType = userType;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View item = inflater.inflate(R.layout.list_item_history, parent, false);

        return new HistoryViewHolder(parent.getContext(), item);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final Consultation consultation = historyList.get(position);

        DateTime dateTime = ISODateTimeFormat.dateTime().parseDateTime(consultation.getDateCreated());
        String dateFormatted = dateTime.toString(DateTimeFormat.forPattern("MMM dd"));

        switch (consultation.getStatus()) {
            case Constants.FIREBASE_CONSULTATION_STATUS_PENDING:

                // TODO: replace with resource string
                holder.setUserName("Pendiente de aprobación");
                holder.setStateIcoImageView(R.drawable.ic_access_time_24dp);

                break;

            case Constants.FIREBASE_CONSULTATION_STATUS_ASSIGNED:

                if (userType.equals(Constants.FIREBASE_USER_TYPE_MEDIC)) {
                    final Patient patient = consultation.getPatient();

                    holder.setUserName(patient.getName());
                } else {
                    final Medic medic = consultation.getMedic();

                    holder.setUserName(medic.getName());
                }

                holder.setStateIcoImageView(R.drawable.ic_chevron_right_24dp);
                holder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemListener.onConsultationClick(consultation.getId());
                    }
                });

                break;

            case Constants.FIREBASE_CONSULTATION_STATUS_CLOSED:
                final Medic medic = consultation.getMedic();

                holder.setUserName(medic.getName());
                holder.setStateIcoImageView(R.drawable.ic_close_24dp);

                break;
        }

        // TODO: replace with dynamic image
        holder.setUserImageView("http://media.graciasdoc.com/pictures/user_placeholder.png");
        holder.setSummaryText(consultation.getDetails().getDescription());
        holder.setDateText(dateFormatted);
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
