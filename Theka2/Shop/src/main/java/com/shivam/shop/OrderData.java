package com.shivam.shop;

public class OrderData {
    private String OrderId;
    private String ContactNo;
    private String Address;
    private String Drinkname;
    private String DrinkSize;
    private String Quantity;
    private String DrinkPrice;
    private String Amount;
    private String Time;
    private String Shopname;
    private String Name;
    private String Uid;

    private String Key;

    public OrderData(String key) {
        Key = key;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public OrderData() {
    }

    public OrderData(String orderId, String contactNo, String address, String drinkname, String drinkSize, String quantity, String drinkPrice, String amount,String time,String shopname,String name,String uid) {
        OrderId = orderId;
        ContactNo = contactNo;
        Address = address;
        Drinkname = drinkname;
        DrinkSize = drinkSize;
        Quantity = quantity;
        DrinkPrice = drinkPrice;
        Amount = amount;
        Time=time;
        Shopname=shopname;
        Name=name;
        Uid=uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShopname() {
        return Shopname;
    }

    public void setShopname(String shopname) {
        Shopname = shopname;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDrinkname() {
        return Drinkname;
    }

    public void setDrinkname(String drinkname) {
        Drinkname = drinkname;
    }

    public String getDrinkSize() {
        return DrinkSize;
    }

    public void setDrinkSize(String drinkSize) {
        DrinkSize = drinkSize;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDrinkPrice() {
        return DrinkPrice;
    }

    public void setDrinkPrice(String drinkPrice) {
        DrinkPrice = drinkPrice;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
