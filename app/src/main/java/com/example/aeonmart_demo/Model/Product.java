package com.example.aeonmart_demo.Model;

public class Product {
    private String MaSp;
    private String Name;
    private double Price;
    private String Category;
    private String Origin;
    private String Description;
    private String Image;
    private boolean FavStatus;
    private String Rate;

    public Product(String maSp, String name, double price, String category, String origin, String description, String image, boolean favStatus, String rate) {
        MaSp = maSp;
        Name = name;
        Price = price;
        Category = category;
        Origin = origin;
        Description = description;
        Image = image;
        FavStatus = favStatus;
        Rate = rate;
    }

    public String getMaSp() {
        return MaSp;
    }

    public void setMaSp(String maSp) {
        MaSp = maSp;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public boolean isFavStatus() {
        return FavStatus;
    }

    public void setFavStatus(boolean favStatus) {
        FavStatus = favStatus;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }
}
