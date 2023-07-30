package com.example.aeonmart_demo.Detail_HoSo_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aeonmart_demo.Activity.ChinhSuaHoSoActivity;
import com.example.aeonmart_demo.Activity.Detail_Profile_Activity;
import com.example.aeonmart_demo.Activity.SignInActivity;
import com.example.aeonmart_demo.R;

public class ProfileActivity extends AppCompatActivity {
Button btn_profile_xemthongtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btn_profile_xemthongtin=findViewById(R.id.profile_BTN_caidat);
        TextView logoutTextView = findViewById(R.id.profile_Tv_DX);
        TextView deleteAccountTextView = findViewById(R.id.profile_Tv_XTK);
        deleteAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gọi phương thức navigateToDeleteAccount() để chuyển hướng sang DeleteAccountActivity
                navigateToDeleteAccount();
            }
        });
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi TextView "Đăng xuất" được nhấn
                logoutAndNavigateToSignIn();
            }
        });
        TextView changePassTextView = findViewById(R.id.profile_Tv_DMK);
        changePassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi TextView "Đổi mật khẩu" được nhấn
                navigateToChangePassword();
            }
        });
        btn_profile_xemthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeeditprofile();
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

    public void changeeditprofile()
    {
        Intent intent=new Intent(ProfileActivity.this, Detail_Profile_Activity.class);
        startActivity(intent);
    }
    public void navigateToChangePassword() {
        Intent intent = new Intent(this, ChangePassActivity.class);
        startActivity(intent);
    }
    private void navigateToDeleteAccount() {
        Intent intent = new Intent(this, DeleteAccountActivity.class);
        startActivity(intent);
    }
}