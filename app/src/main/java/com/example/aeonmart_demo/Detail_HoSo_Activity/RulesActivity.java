package com.example.aeonmart_demo.Detail_HoSo_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aeonmart_demo.R;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Button rulesButton = findViewById(R.id.Rules_button);
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý khi Button được nhấn
                gotoProfile();
            }
        });
    }
    // Phương thức xử lý chuyển trang sang ProfileActivity
    private void gotoProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish(); // Tùy chọn đóng RulesActivity sau khi chuyển sang ProfileActivity
    }
}