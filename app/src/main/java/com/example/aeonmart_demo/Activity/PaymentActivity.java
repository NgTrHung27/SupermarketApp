package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aeonmart_demo.R;

public class PaymentActivity extends AppCompatActivity {
    Button btnXong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnXong = findViewById(R.id.payment_xong);
        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}