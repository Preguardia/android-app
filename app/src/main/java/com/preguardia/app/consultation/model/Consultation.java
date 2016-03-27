package com.preguardia.app.consultation.model;

import com.preguardia.app.user.model.Medic;
import com.preguardia.app.user.model.Patient;

import java.util.List;

/**
 * @author amouly on 3/1/16.
 */
@SuppressWarnings("unused")
public class Consultation {

    private String id;
    private String dateCreated;
    private String dateUpdated;
    private String summary;
    private String category;
    private String details;
    private String status;
    private Patient patient;
    private Medic medic;
    private List<Question> questions;

    public Consultation() {
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public Medic getMedic() {
        return medic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
