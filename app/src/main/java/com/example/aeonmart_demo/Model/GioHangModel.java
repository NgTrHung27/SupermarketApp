package com.example.aeonmart_demo.Model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GioHangModel implements Serializable  {
    private String productImgUrl;
    private String productName;
    private double productPrice;
    private int productQuantity;

    public GioHangModel(String productImgUrl, String productName, double productPrice, int productQuantity) {
        this.productImgUrl = productImgUrl;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public GioHangModel() {
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    // Định nghĩa phương thức để chuyển đổi đối tượng GioHangModel thành HashMap
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("productImgUrl", productImgUrl);
        map.put("productName", productName);
        map.put("productPrice", productPrice);
        map.put("productQuantity", productQuantity);
        return map;
    }
}

