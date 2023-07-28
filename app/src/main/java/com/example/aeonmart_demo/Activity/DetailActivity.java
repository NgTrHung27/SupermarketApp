package com.example.aeonmart_demo.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Model.HomeModel;
import com.example.aeonmart_demo.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    FirebaseFirestore db;
    ArrayList<HomeModel> homeModels;

    ImageView ProductImg;
    TextView Cate, Des, MaSp, Name, Ori, Price, Rate;
    Button btnThemGH;
    CheckBox cbFav;
    NotificationBadge badge;
    EditText edtsoluong;
    HomeModel homeModel;

    String category_DT;
    String description_DT;
    String image_DT;
    String masp_DT;
    String name_DT;
    String origin_DT;
    Double price_DT;
    String rate_DT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        initData();
//        initControl();

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

//    private void initControl() {
//        btnThemGH.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                themGioHang();
//            }
//        });
//    }


//    private void initData() {
//        sanPhamMoi = (HomeModel) getIntent().getSerializableExtra("chitiet");
//        name.setText(sanPhamMoi.getName());
//        mota.setText(sanPhamMoi.getDescription());
//        sx.setText(sanPhamMoi.getOrigin());
//        masp.setText(sanPhamMoi.getMaSp());
//        Glide.with(getApplicationContext()).load(sanPhamMoi.getImage()).into(img);
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        gia.setText("Gia: "+decimalFormat.format(Double.parseDouble(String.valueOf(sanPhamMoi.getPrice())))+"D");
//    }
//    private void themGioHang()
//    {
//        int soluong = Integer.parseInt(toString());
//        long gia = Long.parseLong(String.valueOf(sanPhamMoi.getPrice()))*soluong;
//        GioHang gioHang = new GioHang();
//        gioHang.setGiasp(gia);
//        gioHang.setSoluong(soluong);
//        gioHang.setHinhsp(sanPhamMoi.getImage());
//    }
    }
}