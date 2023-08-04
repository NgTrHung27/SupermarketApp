package com.example.aeonmart_demo.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aeonmart_demo.Adapter.ProductAdapter;
import com.example.aeonmart_demo.MainActivity;
import com.example.aeonmart_demo.Model.ProductModel;
import com.example.aeonmart_demo.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuanlysanphamActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView rv_product;
    private ProductAdapter productAdapter;
    private List<ProductModel> productList;
    private Button btnthemsanpham;
    private static final int UPDATE_PRODUCT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlysanpham);

        db = FirebaseFirestore.getInstance();
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, this); // Pass the context
        rv_product = findViewById(R.id.rv_quanlysanpham);
        rv_product.setLayoutManager(new LinearLayoutManager(this));
        rv_product.setAdapter(productAdapter);

        // Xử lý sự kiện nút thêm sản phẩm
        btnthemsanpham = findViewById(R.id.quanlysanpham_btn_them);
        btnthemsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanlysanphamActivity.this, ThemSanPhamActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện xóa sản phẩm
        productAdapter.setOnProductDeleteListener(new ProductAdapter.OnProductDeleteListener() {
            @Override
            public void onProductDelete(int position) {
                deleteProductFromFirestore(position);

            }
        });

        // update sản phẩm
        productAdapter.setOnProductUpdateListener(new ProductAdapter.OnProductUpdateListener() {
            @Override
            public void onProductUpdate(int position, ProductModel product) {
                // Mở màn hình cập nhật sản phẩm với thông tin đã có
                updateProductInFirestore(position,product);
            }
        });
        fetchProductsFromFirestore();
    }
    // Xử lý kết quả từ màn hình cập nhật sản phẩm
    private void updateProductInFirestore(int position, ProductModel updatedProduct) {

        String maSp = productList.get(position).getMaSp();
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("Name", updatedProduct.getName());
        updatedData.put("Price", updatedProduct.getPrice());
        updatedData.put("Category", updatedProduct.getCategory());
        updatedData.put("Origin", updatedProduct.getOrigin());
        updatedData.put("Description", updatedProduct.getDescription());
        updatedData.put("FavStatus", updatedProduct.isFavStatus());
        updatedData.put("Rate", updatedProduct.getRate());

        db.collection("Product")
                .document(maSp)
                .update(updatedData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(QuanlysanphamActivity.this, "Đã cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
                    // Cập nhật dữ liệu trong danh sách và RecyclerView
                    productList.set(position, updatedProduct);
                    productAdapter.notifyItemChanged(position);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(QuanlysanphamActivity.this, "Lỗi khi cập nhật sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void fetchProductsFromFirestore() {

        db.collection("Product")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        productList.clear(); // Clear the existing list before adding new data
                        for (DocumentSnapshot document : task.getResult()) {
                            ProductModel product = document.toObject(ProductModel.class);
                            productList.add(product);

                        }
                        productAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(QuanlysanphamActivity.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteProductFromFirestore(int position) {
        String maSp = productList.get(position).getMaSp();

        db.collection("Product")
                .document(maSp)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(QuanlysanphamActivity.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                    productList.remove(position);
                    productAdapter.notifyItemRemoved(position);
                    Intent intent=new Intent(QuanlysanphamActivity.this, MainActivity.class);
                    startActivity(intent);

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(QuanlysanphamActivity.this, "Lỗi khi xóa sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
