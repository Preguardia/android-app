package com.preguardia.app.consultation.create.patient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 4/6/16.
 */
public class PatientViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @Bind(R.id.item_patient_name)
    TextView nameTextView;

    public PatientViewHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        ButterKnife.bind(this, itemView);
    }

    public void setName(String name) {
        nameTextView.setText(name);
    }
}