package com.preguardia.app.data.model;

/**
 * @author amouly on 4/25/16.
 */
@SuppressWarnings("unused")
public class Rating {

    private String comment;
    private float score;

    public Rating() {
    }

    public Rating(float score, String comment) {
        this.comment = comment;
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
