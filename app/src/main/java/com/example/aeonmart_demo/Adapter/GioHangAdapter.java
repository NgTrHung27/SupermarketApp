package com.example.aeonmart_demo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Activity.GioHangActivity;
import com.example.aeonmart_demo.Model.GioHangModel;
import com.example.aeonmart_demo.R;

import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {
    private List<GioHangModel> gioHangList;
    public List<GioHangModel> getGioHangList() {
        return gioHangList;
    }
    private Context context;
    private double totalPrice = 0.0;

    // Method to calculate total price of all items in the cart
    public double getTotalPrice() {
        totalPrice = 0.0;
        for (GioHangModel gioHangModel : gioHangList) {
            totalPrice += gioHangModel.getProductPrice() * gioHangModel.getProductQuantity();
        }
        return totalPrice;
    }

    public GioHangAdapter(Context context, List<GioHangModel> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemdonhang, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        GioHangModel gioHangModel = gioHangList.get(position);

        // Load product image using Glide
        Glide.with(context).load(gioHangModel.getProductImgUrl()).into(holder.itemProductImg);

        // Set the product name, price, and quantity in the item view
        holder.itemProductName.setText(gioHangModel.getProductName());
        holder.itemProductPrice.setText(String.format("%sĐ", gioHangModel.getProductPrice()));
        holder.itemProductQuantity.setText(String.format("x%s", gioHangModel.getProductQuantity()));

        // Calculate the total price (thành tiền) for each item (quantity * price)
        double totalPrice = gioHangModel.getProductPrice() * gioHangModel.getProductQuantity();
        holder.tvThanhTien.setText(String.format("%sĐ", totalPrice));
    }


    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder {
        ImageView itemProductImg;
        TextView itemProductName;
        TextView itemProductPrice;
        TextView itemProductQuantity;
        TextView tvThanhTien;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            itemProductImg = itemView.findViewById(R.id.Shop_ImgSP);
            itemProductName = itemView.findViewById(R.id.shop_tvThongTin);
            itemProductPrice = itemView.findViewById(R.id.shop_tvGia);
            itemProductQuantity = itemView.findViewById(R.id.Shop_tvSL);
            tvThanhTien = itemView.findViewById(R.id.Shop_ThanhTien);
        }
    }
}
