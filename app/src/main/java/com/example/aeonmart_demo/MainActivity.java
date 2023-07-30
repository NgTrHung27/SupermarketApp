package com.example.aeonmart_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aeonmart_demo.Admin.QuanlynguoidungActivity;
import com.example.aeonmart_demo.Detail_HoSo_Activity.InforActivity;

public class MainActivity extends AppCompatActivity {
Button admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admin=findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, QuanlynguoidungActivity.class);
                startActivity(intent);
            }
        });
    }
}