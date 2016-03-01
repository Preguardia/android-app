package com.preguardia.app.consultation.details.title;

import com.preguardia.app.consultation.details.generic.GenericItem;

/**
 * @author amouly on 2/29/16.
 */
public class TitleItem extends GenericItem {

    private final String text;

    public TitleItem(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }
}