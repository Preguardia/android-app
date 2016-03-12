package com.preguardia.app.consultation.model;

import java.util.List;

/**
 * @author amouly on 3/1/16.
 */
public class Consultation {

    private String patientId;
    private String dateCreated;
    private String summary;
    private String category;
    private String details;
    private List<Question> questions;

    public Consultation(String patientId, String dateCreated, String summary, String category, String details) {
        this.patientId = patientId;
        this.dateCreated = dateCreated;
        this.summary = summary;
        this.category = category;
        this.details = details;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String title) {
        this.summary = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
