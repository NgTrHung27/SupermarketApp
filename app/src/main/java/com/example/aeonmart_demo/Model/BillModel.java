package com.example.aeonmart_demo.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BillModel implements Serializable{
    private String IdBill;
    private String IdProBill;
    private String NameBill;
    private double PriceBill;
    private int QuantityBill;
    public BillModel() {
        // Hàm khởi tạo mặc định không làm gì cả, nhưng nó là cần thiết để Firestore có thể tạo đối tượng từ dữ liệu cơ sở dữ liệu.
    }

    public BillModel(String idBill, String idProBill, String nameBill, double priceBill, int quantityBill) {
        IdBill = idBill;
        IdProBill = idProBill;
        NameBill = nameBill;
        PriceBill = priceBill;
        QuantityBill = quantityBill;
    }

    public String getIdBill() {
        return IdBill;
    }

    public void setIdBill(String idBill) {
        IdBill = idBill;
    }

    public String getIdProBill() {
        return IdProBill;
    }

    public void setIdProBill(String idProBill) {
        IdProBill = idProBill;
    }

    public String getNameBill() {
        return NameBill;
    }

    public void setNameBill(String nameBill) {
        NameBill = nameBill;
    }

    public double getPriceBill() {
        return PriceBill;
    }

    public void setPriceBill(double priceBill) {
        PriceBill = priceBill;
    }

    public int getQuantityBill() {
        return QuantityBill;
    }

    public void setQuantityBill(int quantityBill) {
        QuantityBill = quantityBill;
    }

    // Định nghĩa phương thức để chuyển đổi đối tượng GioHangModel thành HashMap
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("IdBill", IdBill);
        map.put("IdProbill", IdProBill);
        map.put("NameBill", NameBill);
        map.put("PriceBill", PriceBill);
        map.put("QuantityBill", QuantityBill);
        return map;
    }
}
