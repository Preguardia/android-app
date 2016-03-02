package com.preguardia.app.consultation.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.consultation.details.generic.GenericItem;
import com.preguardia.app.consultation.details.message.MessageItem;
import com.preguardia.app.consultation.details.message.MessageItem2;
import com.preguardia.app.consultation.details.message.MessageViewHolder;
import com.preguardia.app.consultation.details.message.MessageViewHolder2;
import com.preguardia.app.consultation.details.picture.PictureItem;
import com.preguardia.app.consultation.details.picture.PictureViewHolder;
import com.preguardia.app.consultation.details.title.TitleItem;
import com.preguardia.app.consultation.details.title.TitleViewHolder;

import java.util.List;

/**
 * @author amouly on 2/28/16.
 */
public class DetailsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GenericItem> itemsList;

    public DetailsListAdapter(List<GenericItem> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = itemsList.get(position);

        if (item instanceof TitleItem) {
            return 0;
        } else if (item instanceof MessageItem) {
            return 1;
        } else if (item instanceof PictureItem) {
            return 2;
        } else if (item instanceof MessageItem2) {
            return 3;
        }

        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (viewType == 0) {
            View cardHeader = inflater.inflate(R.layout.item_details_first, viewGroup, false);
            viewHolder = new TitleViewHolder(cardHeader);
        } else if (viewType == 1) {
            View cardHeader = inflater.inflate(R.layout.item_details_message, viewGroup, false);
            viewHolder = new MessageViewHolder(cardHeader);
        } else if (viewType == 2) {
            View cardHeader = inflater.inflate(R.layout.item_details_picture, viewGroup, false);
            viewHolder = new PictureViewHolder(cardHeader);
        } else if (viewType == 3) {
            View cardHeader = inflater.inflate(R.layout.item_details_message2, viewGroup, false);
            viewHolder = new MessageViewHolder2(cardHeader);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof TitleViewHolder) {
            final String text = ((TitleItem) itemsList.get(position)).getText();
            final TitleViewHolder messageHolder = (TitleViewHolder) viewHolder;

            messageHolder.setText(text);
        }

        if (viewHolder instanceof MessageViewHolder) {
            final String text = ((MessageItem) itemsList.get(position)).getText();
            final MessageViewHolder messageHolder = (MessageViewHolder) viewHolder;

            messageHolder.setText(text);
        }

        if (viewHolder instanceof MessageViewHolder2) {
            final String text = ((MessageItem2) itemsList.get(position)).getText();
            final MessageViewHolder2 messageHolder2 = (MessageViewHolder2) viewHolder;

            messageHolder2.setText(text);
        }

        if (viewHolder instanceof PictureViewHolder) {
            final String picture = ((PictureItem) itemsList.get(position)).getPicture();
            final PictureViewHolder pictureHolder = (PictureViewHolder) viewHolder;

            pictureHolder.setImage(picture);
        }

    }
}
