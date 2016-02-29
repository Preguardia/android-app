package com.preguardia.app.consultation.details.message;

import com.preguardia.app.consultation.details.generic.GenericItem;

/**
 * @author amouly on 2/29/16.
 */
public class MessageItem extends GenericItem {

    private final String text;

    public MessageItem(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
