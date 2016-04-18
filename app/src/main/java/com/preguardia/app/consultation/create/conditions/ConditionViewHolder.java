package com.preguardia.app.consultation.create.conditions;

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
public class ConditionViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_checkable_name)
    TextView nameTextView;
    @Bind(R.id.item_checkable_check)
    CheckBox checkBoxView;

    public ConditionViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setName(String name) {
        nameTextView.setText(name);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        checkBoxView.setOnClickListener(onClickListener);
    }

    public boolean isChecked() {
        return checkBoxView.isChecked();
    }

    public void setChecked(boolean checked) {
        checkBoxView.setChecked(checked);
    }
}