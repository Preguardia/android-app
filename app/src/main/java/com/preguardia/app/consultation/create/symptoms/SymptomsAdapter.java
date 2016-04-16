package com.preguardia.app.consultation.create.symptoms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.preguardia.app.R;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Aidan Follestad (afollestad)
 */
public class SymptomsAdapter extends SectionedRecyclerViewAdapter<SymptomViewHolder> {

    private List<String> headers;
    private Map<Integer, List<SymptomsItem>> items;

    public SymptomsAdapter(List<String> headers, Map<Integer, List<SymptomsItem>> items) {
        this.headers = headers;
        this.items = items;
    }

    @Override
    public int getSectionCount() {
        return headers.size();
    }

    @Override
    public int getItemCount(int section) {

        switch (section) {
            case 0:
                return items.get(section).size();

            case 1:
                return items.get(section).size();

            case 2:
                return items.get(section).size();

            default:
                return 0;
        }
    }

    @Override
    public void onBindHeaderViewHolder(SymptomViewHolder holder, int section) {
        String header = headers.get(section);

        holder.setName(header);
    }

    @Override
    public void onBindViewHolder(final SymptomViewHolder holder, final int section, final int relativePosition, int absolutePosition) {
        final SymptomsItem tempItem = items.get(section).get(relativePosition);

        holder.setName(tempItem.getName());
        holder.setChecked(tempItem.isSelected());


        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SymptomsItem realItem = items.get(section).get(relativePosition);

                tempItem.setSelected(holder.isChecked());
                realItem.setSelected(holder.isChecked());
            }
        });
    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        return super.getItemViewType(section, relativePosition, absolutePosition);
    }

    @Override
    public SymptomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                layout = R.layout.list_item_symptoms_header;
                break;
            case VIEW_TYPE_ITEM:
                layout = R.layout.list_item_symptoms_main;
                break;
            default:
                layout = R.layout.list_item_main_bold;
                break;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new SymptomViewHolder(v);
    }

    public void replaceData(List<String> headers, Map<Integer, List<SymptomsItem>> items) {
        this.headers = checkNotNull(headers);
        this.items = checkNotNull(items);

        notifyDataSetChanged();
    }
}