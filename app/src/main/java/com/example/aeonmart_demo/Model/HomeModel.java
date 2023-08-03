package com.example.aeonmart_demo.Model;

public class HomeModel {
    String Category;
    String Description;
    Boolean FavStatus;

    String Image;
    String MaSp;
    String Name;
    String Origin;
    Double Price;
    String Rate;

    public HomeModel() {
    }

    public HomeModel(String category, String description, Boolean favStatus, String image, String maSp, String name, String origin, Double price, String rate) {
        Category = category;
        Description = description;
        FavStatus = favStatus;
        Image = image;
        MaSp = maSp;
        Name = name;
        Origin = origin;
        Price = price;
        Rate = rate;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getFavStatus() {
        return FavStatus;
    }

    public void setFavStatus(Boolean favStatus) {
        FavStatus = favStatus;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }
}
