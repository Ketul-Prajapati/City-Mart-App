package com.example.citymart;

import java.io.Serializable;

public class GroceryModel implements Serializable
{
    String gId; // id will be created by firebase realtime database automatically
    String imgUrl; // it will keep the reference link of the uploaded image
    String name;
    String unit;
    int price;
    int stock;

    public GroceryModel() {
    }

    public GroceryModel(String gId, String imgUrl, String name, String unit, int price, int stock) {
        this.gId = gId;
        this.imgUrl = imgUrl;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.stock = stock;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
