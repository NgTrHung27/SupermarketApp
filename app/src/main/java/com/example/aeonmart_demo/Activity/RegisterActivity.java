package com.example.aeonmart_demo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtBirth, edtEmail, edtPassword, edtPhone, edtCCCD, edtAddress;
    private Button btnRegister;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Bind the views
        edtName = findViewById(R.id.Regis_EdText_Name);
        edtBirth = findViewById(R.id.Regis_EdText_Birth);
        edtEmail = findViewById(R.id.Regis_EdText_Em);
        edtPassword = findViewById(R.id.Regis_EdText_MK);
        edtPhone = findViewById(R.id.Regis_EdText_Sdt);
        edtCCCD = findViewById(R.id.Regis_EdText_CCCD);
        edtAddress = findViewById(R.id.Regis_EdText_diachi);
        btnRegister = findViewById(R.id.profile_BTN_caidat);

        // Set click listener for the "Đăng ký" button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // You can add more validation checks for other fields if needed

        // Create a new user in Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful
                            Intent intent  = new  Intent(getApplicationContext(), SignInActivity.class);
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            // You can do further operations here, such as saving user data to Firestore, etc.
                            // For simplicity, I'm not including that part in this code snippet.
                        } else {
                            // Registration failed
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}