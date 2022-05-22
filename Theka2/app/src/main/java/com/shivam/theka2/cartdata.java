package com.shivam.theka2;

public class cartdata {
    public String image;
    public String drinkName;
    public String drinkSize;
    public String drinkPrice;
    public String quantity;
    public String time;
    public String uid;
    public String shopname;
    public String key;

    public cartdata() {
    }

    public cartdata(String image, String drinkName, String drinkSize, String drinkPrice, String quantity, String time, String uid, String shopname, String key) {
        this.image = image;
        this.drinkName = drinkName;
        this.drinkSize = drinkSize;
        this.drinkPrice = drinkPrice;
        this.quantity = quantity;
        this.time = time;
        this.uid = uid;
        this.shopname = shopname;
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(String drinkSize) {
        this.drinkSize = drinkSize;
    }

    public String getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(String drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}