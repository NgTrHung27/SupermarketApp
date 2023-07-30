package com.example.aeonmart_demo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aeonmart_demo.Model.User;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtBirth, edtEmail, edtPassword, edtPhone, edtCCCD, edtAddress;
    private Button btnRegister;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Khởi tạo Firebase Auth và Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Liên kết các view
        edtName = findViewById(R.id.Regis_EdText_Name);
        edtBirth = findViewById(R.id.Regis_EdText_Birth);
        edtEmail = findViewById(R.id.Regis_EdText_Em);
        edtPassword = findViewById(R.id.Regis_EdText_MK);
        edtPhone = findViewById(R.id.Regis_EdText_Sdt);
        edtCCCD = findViewById(R.id.Regis_EdText_CCCD);
        edtAddress = findViewById(R.id.Regis_EdText_diachi);
        btnRegister = findViewById(R.id.Regis_Button_dangky);

        // Thiết lập sự kiện click cho nút "Đăng ký"
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        // Bạn có thể thêm các kiểm tra hợp lệ cho các trường dữ liệu khác nếu cần

        // Tạo một người dùng mới trong Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng ký thành công, lưu thông tin người dùng vào Firestore
                            saveUserInfoToFirestore(email);
                            finish();
                        } else {
                            // Đăng ký thất bại
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void saveUserInfoToFirestore(String email) {
        // Lấy dữ liệu đầu vào từ các trường EditText
        String name = edtName.getText().toString();
        String birth = edtBirth.getText().toString();
        String phone = edtPhone.getText().toString();
        String cccd = edtCCCD.getText().toString();
        String address = edtAddress.getText().toString();
        String password = edtPassword.getText().toString();
        String role="user";


        // Tạo một đối tượng User với thông tin người dùng
        User user = new User(name, birth, email, password, phone, cccd, address,role);

        // Lưu thông tin người dùng vào Firestore
        firestore.collection("users")
                .document(email)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Lưu thông tin người dùng thành công
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            // Chuyển hướng về SignInActivity sau khi đăng ký thành công
                            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                            startActivity(intent);

                        } else {
                            // Lưu thông tin người dùng thất bại
                            Toast.makeText(RegisterActivity.this, "Lỗi: Không thể lưu thông tin người dùng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




}