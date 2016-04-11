package com.preguardia.app.consultation.create.symptoms;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.preguardia.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Aidan Follestad (afollestad)
 */
public class SymptomsAdapter extends SectionedRecyclerViewAdapter<SymptomsAdapter.MainVH> {

    List<String> headers = new ArrayList<>();

    Map<Integer, List<String>> items = new ArrayMap<>();


    public SymptomsAdapter() {
        headers.add("General");
        headers.add("Cabeza/Cuello");
        headers.add("Pecho");

        List<String> list1 = new ArrayList<>();

        list1.add("Fiebre");
        list1.add("Perdida de peso");
        list1.add("Dificultades para dormir");
        list1.add("Cambios de humor");
        list1.add("Cansancio");
        list1.add("Viajes recientes al exterior");
        list1.add("Hospitalizado reciente");

        items.put(0, list1);

        List<String> list2 = new ArrayList<>();

        list2.add("Item4");
        list2.add("Item5");
        list2.add("Item6");

        items.put(1, list2);

        List<String> list3 = new ArrayList<>();

        list3.add("Item7");
        list3.add("Item8");
        list3.add("Item9");

        items.put(2, list3);
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
    public void onBindHeaderViewHolder(MainVH holder, int section) {
        String header = headers.get(section);

        holder.title.setText(header);
    }

    @Override
    public void onBindViewHolder(MainVH holder, int section, int relativePosition, int absolutePosition) {
        String text = items.get(section).get(relativePosition);

        holder.title.setText(text);
    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        return super.getItemViewType(section, relativePosition, absolutePosition);
    }

    @Override
    public MainVH onCreateViewHolder(ViewGroup parent, int viewType) {
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

        return new MainVH(v);
    }

    public static class MainVH extends RecyclerView.ViewHolder {

        final TextView title;

        public MainVH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}