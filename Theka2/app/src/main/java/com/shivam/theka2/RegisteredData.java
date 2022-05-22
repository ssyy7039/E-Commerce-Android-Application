package com.shivam.theka2;

public class RegisteredData {
    private String Name;
    private String Contact;
    private String Email;
    private String Password;
    private String ShopName;
    private String GSTNO;
    private String Address;
    private String Key;

    public RegisteredData() {
    }

    public RegisteredData(String key) {
        Key = key;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }



    public RegisteredData(String name, String contact, String email, String password, String shopName, String GSTNO, String address) {
        Name = name;
        Contact = contact;
        Email = email;
        Password = password;
        ShopName = shopName;
        this.GSTNO = GSTNO;
        Address = address;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getGSTNO() {
        return GSTNO;
    }

    public void setGSTNO(String GSTNO) {
        this.GSTNO = GSTNO;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
