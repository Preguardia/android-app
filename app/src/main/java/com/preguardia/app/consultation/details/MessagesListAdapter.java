package com.preguardia.app.consultation.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.consultation.details.view.MessageLeftViewHolder;
import com.preguardia.app.consultation.details.view.MessageRightViewHolder;
import com.preguardia.app.consultation.model.GenericMessage;

import java.util.List;

/**
 * @author amouly on 2/28/16.
 */
public class MessagesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MESSAGE_TYPE_TITLE = 0;
    private static final int MESSAGE_TYPE_TEXT_RIGHT = 1;
    private static final int MESSAGE_TYPE_TEXT_LEFT = 2;

    private List<GenericMessage> itemsList;

    public MessagesListAdapter(List<GenericMessage> itemsList) {
        this.itemsList = itemsList;
    }

    public void addItem(GenericMessage item) {
        itemsList.add(item);
    }

    @Override
    public int getItemViewType(int position) {
        GenericMessage item = itemsList.get(position);

        switch (item.getType()) {
            case "text": {
                if (item.getFrom().equals("medic")) {
                    return MESSAGE_TYPE_TEXT_LEFT;
                } else {
                    return MESSAGE_TYPE_TEXT_RIGHT;
                }
            }
        }

        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (viewType == MESSAGE_TYPE_TEXT_RIGHT) {
            View card = inflater.inflate(R.layout.item_details_message_right, viewGroup, false);
            viewHolder = new MessageRightViewHolder(card);
        } else if (viewType == MESSAGE_TYPE_TEXT_LEFT) {
            View card = inflater.inflate(R.layout.item_details_message_left, viewGroup, false);
            viewHolder = new MessageLeftViewHolder(card);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

//        if (viewHolder instanceof TitleViewHolder) {
//            final String text = ((TitleItem) itemsList.get(position)).getText();
//            final TitleViewHolder messageHolder = (TitleViewHolder) viewHolder;
//
//            messageHolder.setText(text);
//        }

        if (viewHolder instanceof MessageRightViewHolder) {
            final String text = itemsList.get(position).getContent();
            final MessageRightViewHolder messageHolder = (MessageRightViewHolder) viewHolder;

            messageHolder.setText(text);
        }

        if (viewHolder instanceof MessageLeftViewHolder) {
            final String text = itemsList.get(position).getContent();
            final MessageLeftViewHolder messageHolder2 = (MessageLeftViewHolder) viewHolder;

            messageHolder2.setText(text);
        }

//        if (viewHolder instanceof PictureViewHolder) {
//            final String picture = ((PictureItem) itemsList.get(position)).getPicture();
//            final PictureViewHolder pictureHolder = (PictureViewHolder) viewHolder;
//
//            pictureHolder.setImage(picture);
//        }

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
