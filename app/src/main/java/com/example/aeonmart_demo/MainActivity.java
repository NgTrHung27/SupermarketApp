package com.example.aeonmart_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aeonmart_demo.Admin.QuanlynguoidungActivity;
import com.example.aeonmart_demo.Admin.QuanlysanphamActivity;
import com.example.aeonmart_demo.Detail_HoSo_Activity.InforActivity;

public class MainActivity extends AppCompatActivity {
Button quanlynguoidung,quanlysanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quanlynguoidung=findViewById(R.id.quanlynguoidung_btn);
        quanlysanpham=findViewById(R.id.quanlysampham_btn);
        quanlysanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, QuanlysanphamActivity.class);
                startActivity(intent);
            }
        });
        quanlynguoidung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, QuanlynguoidungActivity.class);
                startActivity(intent);
            }
        });
    }
}