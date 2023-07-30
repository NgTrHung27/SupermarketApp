package com.example.aeonmart_demo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Model.GioHangModel;
import com.example.aeonmart_demo.Model.HomeModel;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    FirebaseFirestore db;
    ArrayList<HomeModel> homeModels;

    ImageView ProductImg;
    TextView Cate, Des, MaSp, Name, Ori, Price, Rate;
    Button btnThemGH;
    CheckBox cbFav;
    NotificationBadge badge;
    EditText edtsoluong;

    String category_DT;
    String description_DT;
    String image_DT;
    String masp_DT;
    String name_DT;
    String origin_DT;
    Double price_DT;
    String rate_DT;
    ImageView imgcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Khởi tạo đối tượng Firestore một lần duy nhất
        db = FirebaseFirestore.getInstance();

        imgcart = findViewById(R.id.img_cart);
        imgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, GioHangActivity.class);
                startActivity(i);
            }
        });

        ProductImg = findViewById(R.id.Detail_Img1);

        Des = findViewById(R.id.Dt_mota);
        MaSp = findViewById(R.id.Dt_MSP);
        Name = findViewById(R.id.Detail_TV);
        Ori = findViewById(R.id.Dt_XS);
        Price = findViewById(R.id.Dt_gia);

        btnThemGH = findViewById(R.id.btn_ThemGioHang);
        cbFav = findViewById(R.id.cbHeart);

        badge = findViewById(R.id.notific);
        edtsoluong = findViewById(R.id.edtsoluong);

        category_DT = getIntent().getStringExtra("category");
        description_DT = getIntent().getStringExtra("description");
        image_DT = getIntent().getStringExtra("image");
        masp_DT = getIntent().getStringExtra("masp");
        name_DT = getIntent().getStringExtra("name");
        origin_DT = getIntent().getStringExtra("origin");
        price_DT = getIntent().getDoubleExtra("price", 0.0);
        rate_DT = getIntent().getStringExtra("rate");

        // Hiển thị dữ liệu lên các TextView
        Name.setText(name_DT);
        Des.setText(description_DT);
        MaSp.setText(masp_DT);
        Ori.setText(origin_DT);
        Price.setText(String.valueOf(price_DT));
        Glide.with(this).load(image_DT).into(ProductImg);

        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = Integer.parseInt(edtsoluong.getText().toString());

                // Tạo một đối tượng GioHangModel mới với thông tin cần thiết
                GioHangModel gioHangModel = new GioHangModel(image_DT, name_DT, price_DT, soLuong);

                // Thêm đối tượng GioHangModel vào danh sách các mục trong giỏ hàng
                GioHangActivity.cartItems.add(gioHangModel);

                // Thông báo cho adapter biết rằng dữ liệu đã thay đổi
                GioHangActivity.adapter.notifyDataSetChanged();

                // Lưu dữ liệu vào Firestore
                saveToFirestore(gioHangModel);
            }
        });
    }

    // Để thêm sản phẩm vào collection "cart" trên Firestore
    private void saveToFirestore(GioHangModel gioHangModel) {
        // Get a reference to the "cart" collection in your Firestore database
        CollectionReference gioHangCollectionRef = db.collection("cart");

        // Tạo một tài liệu mới với một ID tùy ý
        DocumentReference newCartItemRef = gioHangCollectionRef.document();

        // Create a map to represent the data you want to store
        Map<String, Object> cartItemData = new HashMap<>();
        cartItemData.put("image", gioHangModel.getImage());
        cartItemData.put("name", gioHangModel.getName());
        cartItemData.put("price", gioHangModel.getPrice());
        cartItemData.put("quantity", gioHangModel.getSoLuong());

        // Sử dụng set() để thêm dữ liệu vào tài liệu với ID tùy ý
        newCartItemRef.set(cartItemData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data saved successfully to Firestore
                        // You can add any additional handling you want here
                        Toast.makeText(DetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to save data to Firestore
                        // You can add any error handling you want here
                        Toast.makeText(DetailActivity.this, "Lỗi khi lưu dữ liệu vào Firestore", Toast.LENGTH_SHORT).show();

                    }
                });
    }


}
