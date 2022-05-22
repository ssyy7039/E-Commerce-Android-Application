package com.shivam.shop;

public class Drinkdata {
    private String Shopname;
    private String Drinkname;
    private String DrinkPrice;
    private String DrinkSize;
    private String Description;
    private String Imageurl;
    private String key;

    public Drinkdata() {
    }

    public Drinkdata(String key) {
        this.key = key;
    }

    public Drinkdata(String shopname, String drinkname, String drinkPrice, String drinkSize, String description, String imageurl) {
        Shopname = shopname;
        Drinkname = drinkname;
        DrinkPrice = drinkPrice;
        DrinkSize = drinkSize;
        Description = description;
        Imageurl = imageurl;
    }

    public String getShopname() {
        return Shopname;
    }

    public void setShopname(String shopname) {
        Shopname = shopname;
    }

    public String getDrinkname() {
        return Drinkname;
    }

    public void setDrinkname(String drinkname) {
        Drinkname = drinkname;
    }

    public String getDrinkPrice() {
        return DrinkPrice;
    }

    public void setDrinkPrice(String drinkPrice) {
        DrinkPrice = drinkPrice;
    }

    public String getDrinkSize() {
        return DrinkSize;
    }

    public void setDrinkSize(String drinkSize) {
        DrinkSize = drinkSize;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}