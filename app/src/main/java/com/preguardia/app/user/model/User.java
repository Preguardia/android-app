package com.preguardia.app.user.model;

/**
 * @author amouly on 3/17/16.
 */
public class User {

    private String id;
    private String birthDate;
    private String medical;
    private String name;
    private String phone;
    private String plate;
    private String type;
    private String picture;
    private String gcmRegisterId;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGcmRegisterId() {
        return gcmRegisterId;
    }

    public void setGcmRegisterId(String gcmRegisterId) {
        this.gcmRegisterId = gcmRegisterId;
    }
}
