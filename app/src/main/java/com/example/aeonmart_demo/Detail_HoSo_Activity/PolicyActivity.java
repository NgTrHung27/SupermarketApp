package com.example.aeonmart_demo.Detail_HoSo_Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aeonmart_demo.R;

public class PolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("back");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }
}