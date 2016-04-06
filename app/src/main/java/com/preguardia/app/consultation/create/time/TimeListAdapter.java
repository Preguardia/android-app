package com.preguardia.app.consultation.create.time;

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
public class TimeListAdapter extends RecyclerView.Adapter<TimeViewHolder> {

    private final Context context;

    private List<TimeItem> itemsList;

    public TimeListAdapter(Context context, List<TimeItem> itemsList) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public TimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View card = inflater.inflate(R.layout.item_patient_list, viewGroup, false);
        TimeViewHolder viewHolder = new TimeViewHolder(context, card);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TimeViewHolder viewHolder, int position) {
        final String text = itemsList.get(position).getName();

        viewHolder.setName(text);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private void setList(List<TimeItem> items) {
        this.itemsList = checkNotNull(items);
    }

    public void replaceData(List<TimeItem> items) {
        setList(items);
        notifyDataSetChanged();
    }

    public void addItem(TimeItem item) {
        itemsList.add(item);
        notifyDataSetChanged();
    }
}
