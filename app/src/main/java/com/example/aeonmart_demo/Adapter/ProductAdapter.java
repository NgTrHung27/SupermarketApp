package com.example.aeonmart_demo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Model.ProductModel;
import com.example.aeonmart_demo.R;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductModel> productList;
    private Context context;

    private OnProductDeleteListener onProductDeleteListener;

    public interface OnProductDeleteListener {
        void onProductDelete(int position);
    }

    public void setOnProductDeleteListener(OnProductDeleteListener listener) {
        this.onProductDeleteListener = listener;
    }

    public ProductAdapter(List<ProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }
    public ProductAdapter(List<ProductModel> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView maspTextView;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView categoryTextView;
        private TextView originTextView;
        private TextView descriptionTextView;
        private TextView favstatusTextView;
        private TextView rateTextView;
        private ImageView productImageView;
        private Button deleteButton;

        //xóa sản phẩm



        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            maspTextView = itemView.findViewById(R.id.itemproduct_masp);
            nameTextView = itemView.findViewById(R.id.productitem_txt_name);
            priceTextView = itemView.findViewById(R.id.productitem_txt_price);
            categoryTextView = itemView.findViewById(R.id.productitem_txt_category);
            originTextView = itemView.findViewById(R.id.productitem_txt_origin);
            descriptionTextView = itemView.findViewById(R.id.productitem_txt_description);
            favstatusTextView = itemView.findViewById(R.id.productitem_txt_favstatus);
            rateTextView = itemView.findViewById(R.id.productitem_txt_rate);
            productImageView = itemView.findViewById(R.id.productitem_image);
            deleteButton = itemView.findViewById(R.id.productitem_btn_delete);
        }

        public void bind(ProductModel product) {
            maspTextView.setText(product.getMaSp());
            nameTextView.setText(product.getName());
            priceTextView.setText(String.valueOf(product.getPrice()));
            categoryTextView.setText(product.getCategory());
            originTextView.setText(product.getOrigin());
            descriptionTextView.setText(product.getDescription());
            favstatusTextView.setText(String.valueOf(product.isFavStatus()));
            rateTextView.setText(String.valueOf(product.getRate()));

            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .into(productImageView);

            // Xử lý sự kiện nút xóa
            deleteButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Gọi hàm xóa sản phẩm từ Firestore
                    deleteProduct(position);
                }
            });
        }



    }
    private void deleteProduct(int position) {
        // Lấy document ID của sản phẩm tại vị trí position
        String maSp = productList.get(position).getMaSp();

        // Xóa document tương ứng khỏi Firestore
        FirebaseFirestore.getInstance().collection("Product")
                .document(maSp)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Lỗi khi xóa sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                });
    }
}
