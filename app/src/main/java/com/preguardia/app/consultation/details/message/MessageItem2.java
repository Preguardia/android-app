package com.preguardia.app.consultation.details.message;

import com.preguardia.app.consultation.details.generic.GenericItem;

/**
 * @author amouly on 2/29/16.
 */
public class MessageItem2 extends GenericItem {

    private final String text;

    public MessageItem2(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
