package com.preguardia.app.consultation.model;

import java.util.List;

/**
 * @author amouly on 3/1/16.
 */
@SuppressWarnings("unused")
public class Consultation {

    private String id;
    private String patientId;
    private String patientName;
    private String patientMedical;
    private String medicId;
    private String dateCreated;
    private String dateUpdated;
    private String summary;
    private String category;
    private String details;
    private String medicName;
    private String medicPlate;
    private String status;
    private String patientBirthDate;
    private List<Question> questions;

    public Consultation() {
    }

    public Consultation(String patientId, String dateCreated, String status, String summary, String category, String details) {
        this.patientId = patientId;
        this.dateCreated = dateCreated;
        this.status = status;
        this.summary = summary;
        this.category = category;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientMedical() {
        return patientMedical;
    }

    public void setPatientMedical(String patientMedical) {
        this.patientMedical = patientMedical;
    }

    public String getMedicPlate() {
        return medicPlate;
    }

    public void setMedicPlate(String medicPlate) {
        this.medicPlate = medicPlate;
    }

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }
}
