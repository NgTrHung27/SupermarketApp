package com.example.aeonmart_demo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.aeonmart_demo.Detail_HoSo_Activity.ProfileActivity;
import com.example.aeonmart_demo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VoucherListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_list);

        BottomNavigationView bottomNav = findViewById(R.id.VoucList_bottomnav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navHome) {
                    startActivity(new Intent(VoucherListActivity.this, HomeActivity.class));
                    finish();
                } else if (itemId == R.id.navVoucher) {
                    // Do nothing as we are already in VoucherListActivity
                } else if (itemId == R.id.navUser) {
                    startActivity(new Intent(VoucherListActivity.this, ProfileActivity.class));
                    finish();
                }
                return true;
            }
        });

        // Set the selected item to Voucher menu item
        bottomNav.setSelectedItemId(R.id.navVoucher);
    }
}
