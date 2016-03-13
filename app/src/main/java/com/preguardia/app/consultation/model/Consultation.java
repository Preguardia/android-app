package com.preguardia.app.consultation.model;

import java.util.List;

/**
 * @author amouly on 3/1/16.
 */
public class Consultation {

    private String patientId;
    private String medicId;
    private String dateCreated;
    private String summary;
    private String category;
    private String details;
    private String medicName;
    private String status;
    private List<Question> questions;

    public Consultation() {
    }

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

    public String getMedicName() {
        return medicName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMedicName(String medicName) {
        this.medicName = medicName;
    }

    public String getMedicId() {
        return medicId;
    }

    public void setMedicId(String medicId) {
        this.medicId = medicId;
    }
}
