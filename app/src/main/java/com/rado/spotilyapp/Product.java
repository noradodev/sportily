package com.rado.spotilyapp;

import android.graphics.Bitmap;

public class Product {
    private int id;
    private byte[] product_img;
    private String productType;
    private String productName;
    private String weight;
    private String price;
    private String description;
    private int quantity;

    private String productImagePath;

    public Product() {
    }

    public Product(int id, String productType, String productName, String weight, String price, String description) {
        this.id = id;
        this.productType = productType;
        this.productName = productName;
        this.weight = weight;
        this.price = price;
        this.description = description;
        this.quantity = 1;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }


    public byte[] getProductImageBytes() {
        return product_img;
    }


}
