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
    private int selectedPos = 1000;

    public TimeListAdapter(Context context, List<TimeItem> itemsList) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public TimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View card = inflater.inflate(R.layout.list_item_selectable, viewGroup, false);

        return new TimeViewHolder(card);
    }

    @Override
    public void onBindViewHolder(TimeViewHolder viewHolder, final int position) {
        final String text = itemsList.get(position).getName();

        if (selectedPos == position) {
            viewHolder.showCheck();
        } else {
            viewHolder.hideCheck();
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(selectedPos);
                selectedPos = position;
                notifyItemChanged(selectedPos);
            }
        });

        viewHolder.setName(text);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public boolean hasItemSelected() {
        return selectedPos != 1000;
    }

    public TimeItem getSelectedItem() {
        return itemsList.get(selectedPos);
    }

    private void setList(List<TimeItem> items) {
        this.itemsList = checkNotNull(items);
    }

    public void replaceData(List<TimeItem> items) {
        setList(items);
        notifyDataSetChanged();
    }
}
