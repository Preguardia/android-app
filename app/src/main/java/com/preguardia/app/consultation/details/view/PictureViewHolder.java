package com.preguardia.app.consultation.details.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.preguardia.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 2/29/16.
 */
public class PictureViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_details_picture_img)
    ImageView imageView;

    public PictureViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    public void setImage(String url) {

    }
}
