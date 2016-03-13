package com.preguardia.app.consultation.history;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.preguardia.app.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 3/12/16.
 */
public class HistoryViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @Bind(R.id.item_history_user_name)
    TextView userNameView;
    @Bind(R.id.item_history_user_picture)
    ImageView userImageView;
    @Bind(R.id.item_history_summary)
    TextView summaryTextView;
    @Bind(R.id.item_history_state_ico)
    ImageView stateIcoImageView;

    public HistoryViewHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        ButterKnife.bind(this, itemView);
    }

    public void setUserName(String name) {
        userNameView.setText(name);
    }

    public void setUserImageView(String imageUrl) {
        Picasso.with(context)
                .load(imageUrl)
                .into(userImageView);
    }

    public void setSummaryText(String text) {
        summaryTextView.setText(text);
    }

    public void setStateIcoImageView(@DrawableRes int drawable) {
        stateIcoImageView.setImageResource(drawable);
    }
}
