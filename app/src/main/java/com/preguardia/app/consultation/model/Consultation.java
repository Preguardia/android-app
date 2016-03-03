package com.preguardia.app.consultation.model;

import java.util.List;

/**
 * @author amouly on 3/1/16.
 */
public class Consultation {

    private String patient;
    private String dateCreated;
    private String title;
    private String category;
    private String details;
    private List<Question> questions;
    private List<Message> messages;

    public Consultation(String dateCreated, String title, String category, String details, List<Message> messages) {
        this.dateCreated = dateCreated;
        this.title = title;
        this.category = category;
        this.details = details;
        this.messages = messages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
