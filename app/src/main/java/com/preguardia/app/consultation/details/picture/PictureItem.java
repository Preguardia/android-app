package com.preguardia.app.consultation.details.picture;

import com.preguardia.app.consultation.details.generic.GenericItem;

/**
 * @author amouly on 2/29/16.
 */
public class PictureItem extends GenericItem {

    private final String picture;

    public PictureItem(String picture) {
        super();
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }
}
