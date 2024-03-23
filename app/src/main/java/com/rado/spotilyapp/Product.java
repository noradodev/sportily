package com.rado.spotilyapp;

public class Product {
    private int id;
    private String productType;
    private String productName;
    private String weight;
    private String price;
    private String description;

    public Product() {
    }

    public Product(int id, String productType, String productName, String weight, String price, String description) {
        this.id = id;
        this.productType = productType;
        this.productName = productName;
        this.weight = weight;
        this.price = price;
        this.description = description;
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
}
