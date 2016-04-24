package com.preguardia.app.consultation.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.data.model.Consultation;
import com.preguardia.app.data.model.Medic;
import com.preguardia.app.data.model.Patient;
import com.preguardia.app.general.Constants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author amouly on 3/12/16.
 */
public class HistoryListAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private final Context context;
    private final String userType;
    private List<Consultation> historyList;
    private HistoryContract.ConsultationItemListener itemListener;

    public HistoryListAdapter(Context context, List<Consultation> itemsList, String userType, HistoryContract.ConsultationItemListener clickListener) {
        this.context = context;
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

        final Patient patient = consultation.getPatient();
        final Medic medic = consultation.getMedic();

        switch (consultation.getStatus()) {
            case Constants.FIREBASE_CONSULTATION_STATUS_PENDING:

                holder.setUserName(context.getString(R.string.consultation_history_name_pending));
                holder.setStateIcoImageView(R.drawable.ic_access_time_24dp);

                break;

            case Constants.FIREBASE_CONSULTATION_STATUS_ASSIGNED:

                switch (userType) {
                    case Constants.FIREBASE_USER_TYPE_MEDIC:
                        holder.setUserName(patient.getName());

                        break;

                    case Constants.FIREBASE_USER_TYPE_PATIENT:
                        holder.setUserName(medic.getName());
                        break;
                }

                holder.setStateIcoImageView(R.drawable.ic_check_black_24dp);

                break;

            case Constants.FIREBASE_CONSULTATION_STATUS_CLOSED:

                switch (userType) {
                    case Constants.FIREBASE_USER_TYPE_MEDIC:
                        holder.setUserName(patient.getName());

                        break;

                    case Constants.FIREBASE_USER_TYPE_PATIENT:
                        holder.setUserName(medic.getName());

                        break;
                }

                holder.setStateIcoImageView(R.drawable.ic_close_24dp);

                break;
        }

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onConsultationClick(consultation.getId());
            }
        });
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
