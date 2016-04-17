package com.preguardia.app.consultation.create.conditions;

/**
 * @author amouly on 4/11/16.
 */
public class DiseaseItem {

    private final String name;
    private boolean selected;

    public DiseaseItem(String name, boolean selected) {
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
