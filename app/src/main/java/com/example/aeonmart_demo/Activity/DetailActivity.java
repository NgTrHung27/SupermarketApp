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

        // Trong phương thức onClick của nút btnThemGH
        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy số lượng từ EditText
                String quantityStr = edtsoluong.getText().toString();

                // Kiểm tra nếu số lượng rỗng hoặc không phải là một số
                if (quantityStr.isEmpty() || !isNumeric(quantityStr)) {
                    Toast.makeText(DetailActivity.this, "Không được để trống số lượng hoặc số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }


                int quantity = Integer.parseInt(quantityStr);

                // Kiểm tra số lượng phải lớn hơn 0
                if (quantity <= 0) {
                    Toast.makeText(DetailActivity.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Tạo một đối tượng GioHangModel mới
                GioHangModel gioHangModel = new GioHangModel();
                gioHangModel.setProductImgUrl(image_DT);
                gioHangModel.setProductName(name_DT);
                gioHangModel.setProductPrice(price_DT);
                gioHangModel.setProductQuantity(quantity);

                // Gọi hàm để đưa dữ liệu lên Firestore
                addProductToFirestore(gioHangModel);
            }
        });

    }

    // Để thêm sản phẩm vào collection "cart" trên Firestore
    private void addProductToFirestore(GioHangModel gioHangModel) {
        // Lấy tham chiếu đến collection "gio_hang" trong Firestore
        CollectionReference gioHangRef = db.collection("cart");

        // Thêm thông tin sản phẩm vào Firestore với tên tùy chọn (chẳng hạn mã sản phẩm làm tên)
        gioHangRef.document(masp_DT).set(gioHangModel.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Toast.makeText(DetailActivity.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xảy ra lỗi
                        Toast.makeText(DetailActivity.this, "Lỗi khi thêm vào giỏ hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
