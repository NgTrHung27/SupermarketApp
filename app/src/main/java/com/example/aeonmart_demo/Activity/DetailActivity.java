package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aeonmart_demo.Model.GioHang;
import com.example.aeonmart_demo.Model.SanPhamMoi;
import com.example.aeonmart_demo.R;
import com.nex3z.notificationbadge.NotificationBadge;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    ImageView img;
    TextView name,masp,sx,mota,gia;
    Button btnThemGH;
    NotificationBadge badge;
    Spinner spinner;
    SanPhamMoi sanPhamMoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initData();
        initControl();
    }

    private void initControl() {
        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang();
            }
        });
    }

    private void initView() {
        name = findViewById(R.id.Detail_TV);
        badge = findViewById(R.id.notific);
        masp = findViewById(R.id.Dt_MSP);
        sx = findViewById(R.id.Dt_XS);
        mota = findViewById(R.id.Dt_mota);
        img = findViewById(R.id.Detail_Img1);
        spinner = findViewById(R.id.spinner);
        gia = findViewById(R.id.Dt_gia);
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        name.setText(sanPhamMoi.getName());
        mota.setText(sanPhamMoi.getDicreption());
        sx.setText(sanPhamMoi.getOrigin());
        masp.setText(sanPhamMoi.getMasp());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getImage()).into(img);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        gia.setText("Gia: "+decimalFormat.format(Double.parseDouble(String.valueOf(sanPhamMoi.getPrice())))+"D");
    }
    private void themGioHang()
    {
        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
        long gia = Long.parseLong(String.valueOf(sanPhamMoi.getPrice()))*soluong;
        GioHang gioHang = new GioHang();
        gioHang.setGiasp(gia);
        gioHang.setSoluong(soluong);
        gioHang.setHinhsp(sanPhamMoi.getImage());
    }
}