package com.example.aeonmart_demo.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Activity.DetailActivity;
import com.example.aeonmart_demo.Model.HomeModel;
import com.example.aeonmart_demo.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyVH>{
    ArrayList<HomeModel> homeModels;

    public HomeAdapter(ArrayList<HomeModel> homeModels) {
        this.homeModels = homeModels;
    }

    @NonNull
    @Override
    public HomeAdapter.MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_product_cart_view,parent,false);
        return new MyVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyVH holder, int position) {
        HomeModel homeModel = homeModels.get(position);
        holder.textViewCart.setText(homeModel.getName());
        String imageUrl = homeModel.getImage();
        Glide.with(holder.ProductCartView.getContext())
                .load(imageUrl)
                .into(holder.ProductCartView);
        holder.ProductCartView.setOnClickListener(view -> {
            //when click send data to details activity
            Intent sendData2Detail = new Intent(holder.ProductCartView.getContext(), DetailActivity.class);
            sendData2Detail.putExtra("category",homeModels.get(position).getCategory());
            sendData2Detail.putExtra("description",homeModels.get(position).getDescription());
            sendData2Detail.putExtra("image",homeModels.get(position).getImage());
            sendData2Detail.putExtra("masp",homeModels.get(position).getMaSp());
            sendData2Detail.putExtra("name",homeModels.get(position).getName());
            sendData2Detail.putExtra("origin",homeModels.get(position).getOrigin());
            sendData2Detail.putExtra("price",homeModels.get(position).getPrice());
            sendData2Detail.putExtra("rate",homeModels.get(position).getRate());

            //transition animation 2 detail
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity)holder.ProductCartView.getContext(),holder.ProductCartView,
                            "imageMain");
            //sharedElementName is the same as xml file (imageMain)
            holder.ProductCartView.getContext().startActivity(sendData2Detail,optionsCompat.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return homeModels.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {
        ImageView ProductCartView;
        TextView textViewCart;
        public MyVH(@NonNull View itemView) {
            super(itemView);
            ProductCartView = itemView.findViewById(R.id.imageViewProductCart);
            textViewCart = itemView.findViewById(R.id.textviewProductCart);
        }
    }
}
