package com.example.aeonmart_demo.Adapter;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Model.SanPhamMoi;
import com.example.aeonmart_demo.R;

import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {

    Context context;
    List<SanPhamMoi> array;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public SanPhamMoiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detail,parent,false);
        return new  MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamMoiAdapter.MyViewHolder holder, int position) {
            SanPhamMoi sanPhamMoi = array.get(position);
            holder.txt_name.setText(sanPhamMoi.getName());
            holder.txt_gia.setText(sanPhamMoi.getPrice());
            Glide.with(context).load(sanPhamMoi.getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_gia,txt_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_gia = itemView.findViewById(R.id.Dt_gia);
            txt_name = itemView.findViewById(R.id.Detail_TV);
            img = itemView.findViewById(R.id.Detail_Img1);
        }
    }
}
