package com.preguardia.app.consultation.model;

/**
 * @author amouly on 3/1/16.
 */
public class Message {

    private String text;
    private String dateSent;

    public Message(String text, String dateSent) {
        this.text = text;
        this.dateSent = dateSent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }
}
