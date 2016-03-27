package com.preguardia.app.consultation.details.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.preguardia.app.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 2/29/16.
 */
public class MessageLeftViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_details_message_picture)
    ImageView imageView;
    @Bind(R.id.item_details_message_text)
    TextView textView;

    private final Context context;

    public MessageLeftViewHolder(Context context, View view) {
        super(view);

        this.context = context;

        ButterKnife.bind(this, view);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setImage(String pictureUrl) {
        Picasso.with(context)
                .load(pictureUrl)
                .into(imageView);
    }
}
