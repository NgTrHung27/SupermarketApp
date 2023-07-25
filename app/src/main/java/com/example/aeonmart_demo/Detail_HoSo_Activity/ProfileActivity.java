package com.example.aeonmart_demo.Detail_HoSo_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aeonmart_demo.Activity.SignInActivity;
import com.example.aeonmart_demo.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView logoutTextView = findViewById(R.id.profile_Tv_DX);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi TextView "Đăng xuất" được nhấn
                logoutAndNavigateToSignIn();
            }
        });
    }

    // Hàm xử lý đăng xuất và chuyển về trang SignIn
    private void logoutAndNavigateToSignIn() {
        // Thực hiện xử lý đăng xuất ở đây (nếu cần)
        // Ví dụ: Xóa token, xóa thông tin đăng nhập, v.v...

        // Chuyển về trang SignIn (hoặc Login)
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish(); // Đóng activity hiện tại để ngăn người dùng quay lại trang ProfileActivity sau khi đăng xuất
    }
}