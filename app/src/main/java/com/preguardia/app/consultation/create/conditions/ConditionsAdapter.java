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
public class ConditionsAdapter extends RecyclerView.Adapter<ConditionViewHolder> {

    private final Context context;

    private List<ConditionItem> items;

    public ConditionsAdapter(Context context, List<ConditionItem> itemsList) {
        this.items = itemsList;
        this.context = context;
    }

    @Override
    public ConditionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View card = inflater.inflate(R.layout.list_item_checkable, viewGroup, false);

        return new ConditionViewHolder(card);
    }

    @Override
    public void onBindViewHolder(final ConditionViewHolder holder, final int position) {
        final ConditionItem tempItem = items.get(position);

        holder.setName(tempItem.getName());
        holder.setChecked(tempItem.isSelected());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConditionItem realItem = items.get(position);

                tempItem.setSelected(holder.isChecked());
                realItem.setSelected(holder.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void setList(List<ConditionItem> items) {
        this.items = checkNotNull(items);
    }

    public void replaceData(List<ConditionItem> items) {
        setList(items);
        notifyDataSetChanged();
    }

    public void addItem(ConditionItem item) {
        items.add(item);
        notifyDataSetChanged();
    }
}