package com.example.aeonmart_demo.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aeonmart_demo.R;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    ImageView img;
    Text name;
    Button btnThemGH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initData();


    }

    private void initView() {
        img = findViewById(R.id.Detail_Img1);
    }

    private void initData() {
        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang();
            }
        });
    }
    private void themGioHang()
    {
//        if (Utils.manggiohang)
    }
}