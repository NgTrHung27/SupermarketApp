package com.example.aeonmart_demo.Detail_HoSo_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aeonmart_demo.Activity.ChinhSuaHoSoActivity;
import com.example.aeonmart_demo.Activity.Detail_Profile_Activity;
import com.example.aeonmart_demo.Activity.HomeActivity;
import com.example.aeonmart_demo.Activity.SignInActivity;
import com.example.aeonmart_demo.Activity.VoucherListActivity;
import com.example.aeonmart_demo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
Button btn_profile_xemthongtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNav = findViewById(R.id.Profile_bottomnav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navHome) {
                    startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                    finish();
                } else if (itemId == R.id.navVoucher) {
                    startActivity(new Intent(ProfileActivity.this, VoucherListActivity.class));
                    finish();                }
                else if (itemId == R.id.navUser) {
                }
                return true;
            }
        });
        // Set the selected item to Voucher menu item
        bottomNav.setSelectedItemId(R.id.navUser);

        btn_profile_xemthongtin=findViewById(R.id.profile_BTN_xemthongtin);
        TextView logoutTextView = findViewById(R.id.profile_Tv_DX);
        TextView deleteAccountTextView = findViewById(R.id.profile_Tv_XTK);
        TextView tvRules = findViewById(R.id.profile_Tv_DK);
        TextView tvInfor = findViewById(R.id.profile_Tv_Lienlac);
        TextView tvPolicy = findViewById(R.id.profile_Tv_QD);
        TextView changePassTextView = findViewById(R.id.profile_Tv_DMK);
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
        tvPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến RulesActivity khi TextView được nhấn
                navigateToPolicy();
            }
        });
        tvInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến RulesActivity khi TextView được nhấn
                navigateToInfor();
            }
        });
        tvRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến RulesActivity khi TextView được nhấn
                navigateToRules();
            }
        });


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
    private void navigateToRules() {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }
    private void navigateToInfor() {
        Intent intent = new Intent(this, InforActivity.class);
        startActivity(intent);
    }
    private void navigateToPolicy() {
        Intent intent = new Intent(this, PolicyActivity.class);
        startActivity(intent);
    }
}