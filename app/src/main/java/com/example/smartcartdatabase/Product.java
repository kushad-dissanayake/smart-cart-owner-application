package com.example.smartcartdatabase;

public class Product {
    private String id;
    private String name;
    private String price;
    private String productCount;
    private String discount;

    public Product() {
    }

    public Product(String id, String name, String price, String productCount, String discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productCount = productCount;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
