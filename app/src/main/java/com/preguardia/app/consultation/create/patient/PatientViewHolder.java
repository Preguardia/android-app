package com.preguardia.app.consultation.create.patient;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 4/6/16.
 */
public class PatientViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_selectable_name)
    TextView nameTextView;
    @Bind(R.id.item_selectable_check)
    ImageView checkImageView;

    public PatientViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setName(String name) {
        nameTextView.setText(name);
    }

    public void showCheck() {
        checkImageView.setVisibility(View.VISIBLE);
    }

    public void hideCheck() {
        checkImageView.setVisibility(View.GONE);
    }
}