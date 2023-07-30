package com.example.aeonmart_demo.Detail_HoSo_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aeonmart_demo.Activity.SignInActivity;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteAccountActivity extends AppCompatActivity {
    private Button btnYes;
    private Button btnNo;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức thực hiện xóa tài khoản
                deleteAccount();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang trước đó (hoặc màn hình chính)
                finish();
            }
        });
    }

    private void deleteAccount() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // Xóa dữ liệu tương ứng của người dùng trong Firestore (nếu có)
            String email = user.getEmail();
            if (email != null) {
                firestore.collection("users").document(email).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Xóa dữ liệu thành công, tiếp tục xóa tài khoản trong Firebase Authentication
                                user.delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Xóa tài khoản thành công
                                                Toast.makeText(DeleteAccountActivity.this, "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                                                // Chuyển hướng về trang SignInActivity sau khi xóa tài khoản thành công
                                                Intent intent = new Intent(DeleteAccountActivity.this, SignInActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Xóa tài khoản thất bại
                                                Toast.makeText(DeleteAccountActivity.this, "Lỗi: Không thể xóa tài khoản", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Xóa dữ liệu thất bại
                                Toast.makeText(DeleteAccountActivity.this, "Lỗi: Không thể xóa dữ liệu người dùng", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Không thể lấy email của người dùng
                Toast.makeText(DeleteAccountActivity.this, "Lỗi: Không thể lấy email người dùng", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Không có người dùng đăng nhập
            Toast.makeText(DeleteAccountActivity.this, "Lỗi: Không có người dùng đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }
}