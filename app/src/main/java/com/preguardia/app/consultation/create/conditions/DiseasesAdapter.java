package com.preguardia.app.consultation.create.conditions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author amouly on 4/11/16.
 */
public class DiseasesAdapter extends RecyclerView.Adapter<DiseaseViewHolder> {

    private final Context context;

    private List<DiseaseItem> items;

    public DiseasesAdapter(Context context, List<DiseaseItem> itemsList) {
        this.items = itemsList;
        this.context = context;
    }

    @Override
    public DiseaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View card = inflater.inflate(R.layout.list_item_checkable, viewGroup, false);

        return new DiseaseViewHolder(card);
    }

    @Override
    public void onBindViewHolder(final DiseaseViewHolder holder, final int position) {
        final DiseaseItem tempItem = items.get(position);

        holder.setName(tempItem.getName());
        holder.setChecked(tempItem.isSelected());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiseaseItem realItem = items.get(position);

                tempItem.setSelected(holder.isChecked());
                realItem.setSelected(holder.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void setList(List<DiseaseItem> items) {
        this.items = checkNotNull(items);
    }

    public void replaceData(List<DiseaseItem> items) {
        setList(items);
        notifyDataSetChanged();
    }

    public void addItem(DiseaseItem item) {
        items.add(item);
        notifyDataSetChanged();
    }
}