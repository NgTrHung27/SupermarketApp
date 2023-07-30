package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aeonmart_demo.R;

public class IntroActivity extends AppCompatActivity {
Button intro_btn_DK,intro_btn_DN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        intro_btn_DN =findViewById(R.id.introLoginButton);
        intro_btn_DK =findViewById(R.id.introSigupButton);

        intro_btn_DK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(IntroActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        intro_btn_DN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(IntroActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });

    }
}