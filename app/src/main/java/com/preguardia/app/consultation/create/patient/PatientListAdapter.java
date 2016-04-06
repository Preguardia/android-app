package com.preguardia.app.consultation.create.patient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author amouly on 4/6/16.
 */
public class PatientListAdapter extends RecyclerView.Adapter<PatientViewHolder> {

    private final Context context;

    private List<PatientItem> itemsList;

    public PatientListAdapter(Context context, List<PatientItem> itemsList) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View card = inflater.inflate(R.layout.item_patient_list, viewGroup, false);
        PatientViewHolder viewHolder = new PatientViewHolder(context, card);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PatientViewHolder viewHolder, int position) {
        final String text = itemsList.get(position).getName();

        viewHolder.setName(text);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private void setList(List<PatientItem> items) {
        this.itemsList = checkNotNull(items);
    }

    public void replaceData(List<PatientItem> items) {
        setList(items);
        notifyDataSetChanged();
    }

    public void addItem(PatientItem item) {
        itemsList.add(item);
        notifyDataSetChanged();
    }
}