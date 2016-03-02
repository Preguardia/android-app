package com.preguardia.app.consultation.details.message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 2/29/16.
 */
public class MessageViewHolder2 extends RecyclerView.ViewHolder {

    @Bind(R.id.item_details_message_text)
    TextView textView;

    public MessageViewHolder2(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
