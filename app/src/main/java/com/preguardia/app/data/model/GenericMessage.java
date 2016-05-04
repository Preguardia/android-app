package com.preguardia.app.data.model;

/**
 * @author amouly on 2/29/16.
 */
public class GenericMessage {

    private String type;
    private String content;
    private String from;
    private String dateSent;

    public GenericMessage() {
    }

    public GenericMessage(String content, String type, String from) {
        this.type = type;
        this.content = content;
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
