package com.example.aeonmart_demo.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Activity.DetailActivity;
import com.example.aeonmart_demo.Model.FavouriteModel;
import com.example.aeonmart_demo.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyVH> {
    FirebaseFirestore db;
    ArrayList<FavouriteModel> favouriteModels;

    public FavouriteAdapter(ArrayList<FavouriteModel> favouriteModels) {
        this.favouriteModels = favouriteModels;
    }
    @NonNull
    @Override
    public FavouriteAdapter.MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_product_cart_view,parent,false);
        return new MyVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.MyVH holder, int position) {
        FavouriteModel favouriteModel = favouriteModels.get(position);
        holder.TextFav.setText(favouriteModel.getNameFav());
        String imageUrl = favouriteModel.getImageFav();
        Glide.with(holder.ProductCartFav.getContext())
                .load(imageUrl)
                .into(holder.ProductCartFav);
        holder.ProductCartFav.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), DetailActivity.class);
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return favouriteModels.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {
        ImageView ProductCartFav;
        TextView  TextFav;

        public MyVH(@NonNull View itemView) {
            super(itemView);
            ProductCartFav= itemView.findViewById(R.id.imageViewProductCart);
            TextFav= itemView.findViewById(R.id.textviewProductCart);

        }
    }
}
