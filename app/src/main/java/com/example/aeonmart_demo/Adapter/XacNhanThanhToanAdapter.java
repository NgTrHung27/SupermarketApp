package com.example.aeonmart_demo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Model.GioHangModel;
import com.example.aeonmart_demo.R;

import java.util.List;

public class XacNhanThanhToanAdapter extends RecyclerView.Adapter<XacNhanThanhToanAdapter.ViewHolder> {

    private List<GioHangModel> gioHangList;

    // Constructor để truyền danh sách dữ liệu vào Adapter
    public XacNhanThanhToanAdapter(List<GioHangModel> gioHangList) {
        this.gioHangList = gioHangList;
    }

    // ViewHolder để giữ các thành phần view trong mỗi item của RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        ImageView img;
        // Các view khác nếu cần thiết

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//             Khởi tạo các view trong item của RecyclerView
            productNameTextView = itemView.findViewById(R.id.xndh_tvThongTin);
            productPriceTextView = itemView.findViewById(R.id.xndh_tvGia);
            img = itemView.findViewById(R.id.xndh_ImgSP);
            // Khởi tạo các view khác nếu cần thiết
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Khởi tạo item view bằng cách inflate layout của mỗi item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xac_nhan_thanh_toan, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Hiển thị dữ liệu từ danh sách gioHangList vào các view trong item view
        GioHangModel gioHangModel = gioHangList.get(position);
        holder.productNameTextView.setText(gioHangModel.getProductName());
        holder.productPriceTextView.setText(String.valueOf(gioHangModel.getProductPrice()));
        Glide.with(holder.itemView.getContext())
                .load(gioHangModel.getProductImgUrl())
                .centerCrop()
                .into(holder.img);
        // Hiển thị các dữ liệu khác nếu cần thiết
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }
}
