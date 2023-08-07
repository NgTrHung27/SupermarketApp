package com.example.aeonmart_demo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aeonmart_demo.Activity.BillActivity;
import com.example.aeonmart_demo.Model.BillModel;
import com.example.aeonmart_demo.Model.GioHangModel;
import com.example.aeonmart_demo.R;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    List<BillModel> billModels;
    private List<GioHangModel> gioHangList;
    Context context;

    public BillAdapter( Context context, List<GioHangModel> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;}

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        // Hiển thị dữ liệu từ danh sách gioHangList vào các view trong item view
        GioHangModel gioHangModel = gioHangList.get(position);
        holder.NameBill.setText(gioHangModel.getProductName());
        holder.PriceBill.setText(String.valueOf(gioHangModel.getProductPrice()));
        holder.QuantityBill.setText(String.valueOf(gioHangModel.getProductQuantity()));
        double totalPrice = gioHangModel.getProductPrice() * gioHangModel.getProductQuantity();
        holder.ThanhTienBill.setText(String.format("%sĐ", totalPrice));
        // Hiển thị các dữ liệu khác nếu cần thiết
    }

    @Override
    public int getItemCount() { return gioHangList.size();}

    public class BillViewHolder extends RecyclerView.ViewHolder {
        // Hiển thị dữ liệu từ danh sách gioHangList vào các view trong item view
        TextView NameBill,PriceBill,QuantityBill,ThanhTienBill;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);

            NameBill = itemView.findViewById(R.id.Bill_name);
            PriceBill = itemView.findViewById(R.id.Bill_Price);
            QuantityBill = itemView.findViewById(R.id.Bill_quantity);
            ThanhTienBill = itemView.findViewById(R.id.Bill_ThanhTien);
        }
    }
}
