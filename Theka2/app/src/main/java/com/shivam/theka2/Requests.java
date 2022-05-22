package com.shivam.theka2;

import java.util.List;

public class Requests {
    private String Name;
    private String Contact;
    private String Address;
    private String Time;
    private String uid;
    private String Shopname;
    private String Key;
    private String OrderId;
    private List<cartdata> Drinks;
    private String Amount;
    private String Status;


    public Requests() {
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public Requests(String name, String contact, String address, String time, String uid, String shopname, String key, String orderId, List<cartdata> drinks, String amount, String status) {
        Name = name;
        Contact = contact;
        Address = address;
        Time = time;
        this.uid = uid;
        Shopname = shopname;
        Key = key;
        OrderId = orderId;
        Drinks = drinks;
        Amount = amount;
        Status = status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getShopname() {
        return Shopname;
    }

    public void setShopname(String shopname) {
        Shopname = shopname;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public List<cartdata> getDrinks() {
        return Drinks;
    }

    public void setDrinks(List<cartdata> drinks) {
        Drinks = drinks;
    }
}