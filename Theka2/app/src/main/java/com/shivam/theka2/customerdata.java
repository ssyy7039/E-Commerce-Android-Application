package com.shivam.theka2;

public class customerdata {
    private String Name;
    private String ContactNo;
    private String Email;
    private String Address;

    public customerdata() {
    }

    public customerdata(String name, String contactNo, String email,  String address) {
        Name = name;
        ContactNo = contactNo;
        Email = email;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
