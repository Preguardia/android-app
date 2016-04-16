package com.preguardia.app.consultation.create.symptoms;

/**
 * @author amouly on 4/6/16.
 */
public class SymptomsItem {

    private final String name;
    private boolean selected;

    public SymptomsItem(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
