package com.example.aeonmart_demo.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Model.ProductModel;
import com.example.aeonmart_demo.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CapnhatsanphamActivity extends AppCompatActivity {
    private TextView maspTextView;
    private EditText nameTextView;
    private EditText priceTextView;
    private EditText categoryTextView;
    private EditText originTextView;
    private EditText descriptionTextView;
    private TextView favstatusTextView;
    private EditText rateTextView;
    private ImageView productImageView;
    private Button btn_update,btn_back ;

    String category_DT;
    String description_DT;
    String image_DT;
    String masp_DT;
    String name_DT;
    String origin_DT;
    Double price_DT;
    String rate_DT;
    Boolean favStatus_DT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capnhatsanpham);

        maspTextView = findViewById(R.id.updateproduct_txt_masp);
        nameTextView = findViewById(R.id.updateproduct_edt_name);
        priceTextView = findViewById(R.id.updateproduct_edt_price);
        categoryTextView = findViewById(R.id.updateproduct_edt_category);
        originTextView = findViewById(R.id.updateproduct_edt_origin);
        descriptionTextView = findViewById(R.id.updateproduct_edt_description);
        favstatusTextView = findViewById(R.id.updateproduct_txt_favstatus);
        rateTextView = findViewById(R.id.updateproduct_edt_rate);
        productImageView = findViewById(R.id.updateproduct_image);
        btn_update = findViewById(R.id.updateproduct_btn_update);
        btn_back=findViewById(R.id.updateproduct_btn_back);
        //lấy dữ liệu
        category_DT = getIntent().getStringExtra("Category");
        description_DT = getIntent().getStringExtra("Description");
        image_DT = getIntent().getStringExtra("Image");
        masp_DT = getIntent().getStringExtra("Masp");
        name_DT = getIntent().getStringExtra("Name");
        origin_DT = getIntent().getStringExtra("Origin");
        price_DT = getIntent().getDoubleExtra("Price", 0.0);
        rate_DT = getIntent().getStringExtra("Rate");
        favStatus_DT = getIntent().getBooleanExtra("FavStatus", false);

        // Điền dữ liệu vào các trường giao diện
        maspTextView.setText(masp_DT);
        nameTextView.setText(name_DT);
        priceTextView.setText(String.valueOf(price_DT));
        categoryTextView.setText(category_DT);
        originTextView.setText(origin_DT);
        descriptionTextView.setText(description_DT);
        favstatusTextView.setText(String.valueOf(favStatus_DT));
        rateTextView.setText(rate_DT);
        Glide.with(this).load(image_DT).into(productImageView);

        btn_update.setOnClickListener(view -> {
            // Lấy dữ liệu đã chỉnh sửa từ giao diện và cập nhật vào Firestore
            Map<String, Object> updatedProductData = new HashMap<>();
            updatedProductData.put("Category", categoryTextView.getText().toString());
            updatedProductData.put("Description", descriptionTextView.getText().toString());
            updatedProductData.put("Image", image_DT);
            updatedProductData.put("Name", nameTextView.getText().toString());
            updatedProductData.put("Origin", originTextView.getText().toString());
            updatedProductData.put("Price", Double.parseDouble(priceTextView.getText().toString()));
            updatedProductData.put("Rate", rateTextView.getText().toString());
            updatedProductData.put("FavStatus", favStatus_DT);

            // Thực hiện cập nhật thông tin sản phẩm lên Firestore
            FirebaseFirestore.getInstance().collection("Product")
                    .document(masp_DT)
                    .update(updatedProductData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Thông tin sản phẩm đã được cập nhật", Toast.LENGTH_SHORT).show();
                        finish(); // Kết thúc hoạt động sau khi cập nhật
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi cập nhật thông tin sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CapnhatsanphamActivity.this,QuanlysanphamActivity.class);
                startActivity(intent);
            }
        });
    }
}
