package com.shivam.shop;

public class Feedbackdata {
    private String Feedback;
    private String Shopname;
    private String Contact;
    private String Email;
    private String UID;
    private String Time;
    private String Key;

    public Feedbackdata(String key) {
        Key = key;
    }

    public Feedbackdata() {
    }

    public Feedbackdata(String feedback, String shopname, String contact, String email, String UID,String time) {
        Feedback = feedback;
        Shopname = shopname;
        Contact = contact;
        Email = email;
        this.UID = UID;
        Time=time;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getShopname() {
        return Shopname;
    }

    public void setShopname(String shopname) {
        Shopname = shopname;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
