package com.preguardia.app.consultation.create.diseases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.consultation.create.patient.PatientItem;
import com.preguardia.app.consultation.create.patient.PatientViewHolder;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author amouly on 4/11/16.
 */
public class DiseasesAdapter extends RecyclerView.Adapter<PatientViewHolder> {

    private final Context context;

    private List<DiseaseItem> itemsList;

    public DiseasesAdapter(Context context, List<DiseaseItem> itemsList) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View card = inflater.inflate(R.layout.list_item_patient, viewGroup, false);
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

    private void setList(List<DiseaseItem> items) {
        this.itemsList = checkNotNull(items);
    }

    public void replaceData(List<DiseaseItem> items) {
        setList(items);
        notifyDataSetChanged();
    }

    public void addItem(DiseaseItem item) {
        itemsList.add(item);
        notifyDataSetChanged();
    }
}