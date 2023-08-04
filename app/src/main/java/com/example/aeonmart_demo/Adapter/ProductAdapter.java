package com.example.aeonmart_demo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
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

    public interface OnProductUpdateListener {
        void onProductUpdate(int position, ProductModel product);

    }

    private OnProductUpdateListener onProductUpdateListener;

    public void setOnProductUpdateListener(OnProductUpdateListener listener) {
        this.onProductUpdateListener = listener;
    }
    public interface OnProductEditListener {
        void onProductEdit(int position, ProductModel updatedProduct);
    }

    private OnProductEditListener onProductEditListener;

    public void setOnProductEditListener(OnProductEditListener listener) {
        this.onProductEditListener = listener;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView maspTextView;
        private EditText nameTextView;
        private EditText priceTextView;
        private EditText categoryTextView;
        private EditText originTextView;
        private EditText descriptionTextView;
        private TextView favstatusTextView;
        private EditText rateTextView;
        private ImageView productImageView;
        private Button deleteButton;
        private Button updateButton;

        //xóa sản phẩm



        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            maspTextView = itemView.findViewById(R.id.itemproduct_edt_masp);
            nameTextView = itemView.findViewById(R.id.productitem_edt_name);
            priceTextView = itemView.findViewById(R.id.productitem_edt_price);
            categoryTextView = itemView.findViewById(R.id.productitem_edt_category);
            originTextView = itemView.findViewById(R.id.productitem_edt_origin);
            descriptionTextView = itemView.findViewById(R.id.productitem_edt_description);
            favstatusTextView = itemView.findViewById(R.id.productitem_txt_favstatus);
            rateTextView = itemView.findViewById(R.id.productitem_edt_rate);
            productImageView = itemView.findViewById(R.id.productitem_image);
            deleteButton = itemView.findViewById(R.id.productitem_btn_delete);
            updateButton=itemView.findViewById(R.id.productitem_btn_update);

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



// set dữ liệu

            maspTextView.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && onProductEditListener != null) {
                    product.setMaSp(v.getText().toString());
                    onProductEditListener.onProductEdit(getAdapterPosition(), product);
                }
                return true;
            });

            nameTextView.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && onProductEditListener != null) {
                    product.setName(v.getText().toString());
                    onProductEditListener.onProductEdit(getAdapterPosition(), product);
                }
                return false;
            });
            priceTextView.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && onProductEditListener != null) {
                    double newPrice = Double.parseDouble(v.getText().toString());
                    product.setPrice(newPrice);
                    onProductEditListener.onProductEdit(getAdapterPosition(), product);
                }
                return false;
            });

            categoryTextView.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && onProductEditListener != null) {
                    product.setCategory(v.getText().toString());
                    onProductEditListener.onProductEdit(getAdapterPosition(), product);
                }
                return false;
            });
            originTextView.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && onProductEditListener != null) {
                    product.setOrigin(v.getText().toString());
                    onProductEditListener.onProductEdit(getAdapterPosition(), product);
                }
                return false;
            });

            descriptionTextView.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && onProductEditListener != null) {
                    product.setDescription(v.getText().toString());
                    onProductEditListener.onProductEdit(getAdapterPosition(), product);
                }
                return false;
            });

            rateTextView.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && onProductEditListener != null) {
                    product.setRate(v.getText().toString());
                    onProductEditListener.onProductEdit(getAdapterPosition(), product);
                }
                return false;
            });
            // Xử lý sự kiện nút xóa
            deleteButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Gọi hàm xóa sản phẩm từ Firestore
                    deleteProduct(position);
                }
            });
            updateButton.setOnClickListener(view -> {
                if (onProductUpdateListener != null) {
                    onProductUpdateListener.onProductUpdate(getAdapterPosition(), product);
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
