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
import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.CartViewHolder> {
    private ArrayList<GioHangModel> cartItems;

    public GioHangAdapter(ArrayList<GioHangModel> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdonhang, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        GioHangModel cartItem = cartItems.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        // Declare the views to populate the cart item data
        private ImageView imageViewCartItem;
        private TextView textViewCartItemName;
        private TextView textViewCartItemPrice;
        private TextView textViewCartItemQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views
            imageViewCartItem = itemView.findViewById(R.id.Shop_ImgSP);
            textViewCartItemName = itemView.findViewById(R.id.shop_tvThongTin);
            textViewCartItemPrice = itemView.findViewById(R.id.shop_tvGia);
            textViewCartItemQuantity = itemView.findViewById(R.id.Shop_tvSL);
        }

        public void bind(GioHangModel cartItem) {
            // Bind the cart item data to the views
            Glide.with(itemView.getContext()).load(cartItem.getImage()).into(imageViewCartItem);
            textViewCartItemName.setText(cartItem.getName());
            textViewCartItemPrice.setText(String.valueOf(cartItem.getPrice()));
            textViewCartItemQuantity.setText("x" + cartItem.getSoLuong());
        }
    }
}

