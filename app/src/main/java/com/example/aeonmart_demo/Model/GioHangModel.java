package com.example.aeonmart_demo.Model;

import java.io.Serializable;

public class GioHangModel implements Serializable {
    String Image;
    String Name;
    Double Price;
    int SoLuong;

    public GioHangModel(String image, String name, Double price, int soLuong) {
        Image = image;
        Name = name;
        Price = price;
        SoLuong = soLuong;
    }

    public GioHangModel() {
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }
}
