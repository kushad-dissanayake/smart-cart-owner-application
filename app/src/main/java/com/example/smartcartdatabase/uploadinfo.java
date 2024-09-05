package com.example.smartcartdatabase;

public class uploadinfo {
    public String imageName;
    public String imageURL;
    public String price;

    public uploadinfo(){}

    public uploadinfo(String name, String url, String price) {
        this.imageName = name;
        this.imageURL = url;
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPrice() {
        return price;
    }
}
