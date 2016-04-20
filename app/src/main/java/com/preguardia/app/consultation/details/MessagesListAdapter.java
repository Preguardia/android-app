package com.preguardia.app.consultation.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;
import com.preguardia.app.consultation.details.view.MessageLeftViewHolder;
import com.preguardia.app.consultation.details.view.MessageRightViewHolder;
import com.preguardia.app.data.model.GenericMessage;
import com.preguardia.app.general.Constants;

import java.util.List;

/**
 * @author amouly on 2/28/16.
 */
public class MessagesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MESSAGE_TYPE_TITLE = 0;
    private static final int MESSAGE_TYPE_TEXT_RIGHT = 1;
    private static final int MESSAGE_TYPE_TEXT_LEFT = 2;

    private final Context context;
    private final String userType;

    private List<GenericMessage> itemsList;

    public MessagesListAdapter(Context context, String userType, List<GenericMessage> itemsList) {
        this.itemsList = itemsList;
        this.context = context;
        this.userType = userType;
    }

    public void addItem(GenericMessage item) {
        itemsList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        GenericMessage item = itemsList.get(position);

        switch (item.getType()) {
            case "text": {
                boolean isFromMedic = item.getFrom().equals(Constants.FIREBASE_USER_TYPE_MEDIC);
                boolean isUserMedic = userType.equals(Constants.FIREBASE_USER_TYPE_MEDIC);

                if (isFromMedic && isUserMedic) {
                    return MESSAGE_TYPE_TEXT_RIGHT;
                } else if (!isFromMedic && isUserMedic) {
                    return MESSAGE_TYPE_TEXT_LEFT;
                } else if (isFromMedic && !isUserMedic) {
                    return MESSAGE_TYPE_TEXT_LEFT;
                } else if (!isFromMedic && !isUserMedic) {
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
            viewHolder = new MessageRightViewHolder(context, card);
        } else if (viewType == MESSAGE_TYPE_TEXT_LEFT) {
            View card = inflater.inflate(R.layout.item_details_message_left, viewGroup, false);
            viewHolder = new MessageLeftViewHolder(context, card);
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
            messageHolder.setImage("http://media.graciasdoc.com/pictures/user_placeholder.png");
        }

        if (viewHolder instanceof MessageLeftViewHolder) {
            final String text = itemsList.get(position).getContent();
            final MessageLeftViewHolder messageHolder2 = (MessageLeftViewHolder) viewHolder;

            messageHolder2.setText(text);
            messageHolder2.setImage("http://media.graciasdoc.com/pictures/user_placeholder.png");
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
