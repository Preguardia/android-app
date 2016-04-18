package com.preguardia.app.consultation.create.symptoms;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 4/6/16.
 */
public class SymptomViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_checkable_name)
    TextView nameTextView;
    @Bind(R.id.item_checkable_check)
    @Nullable
    CheckBox checkBoxView;

    public SymptomViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setName(String name) {
        nameTextView.setText(name);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        if (checkBoxView != null) {
            checkBoxView.setOnClickListener(onClickListener);
        }
    }

    public boolean isChecked() {
        return checkBoxView != null && checkBoxView.isChecked();
    }

    public void setChecked(boolean checked) {
        if (checkBoxView != null) {
            checkBoxView.setChecked(checked);
        }
    }
}