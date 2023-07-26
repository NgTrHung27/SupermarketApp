package com.example.aeonmart_demo.Detail_HoSo_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aeonmart_demo.Activity.SignInActivity;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends AppCompatActivity {
    private EditText etOldPassword;
    private EditText etNewPassword;
    private Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mật khẩu cũ và mật khẩu mới từ EditText
                String oldPassword = etOldPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();

                // Gọi phương thức thực hiện việc đổi mật khẩu
                changePassword(oldPassword, newPassword);
            }
        });
    }

    private void changePassword(String oldPassword, String newPassword) {
        // Lấy người dùng hiện tại
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Xác thực người dùng lại bằng mật khẩu cũ trước khi thay đổi mật khẩu
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Xác thực thành công, tiến hành thay đổi mật khẩu mới
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Đổi mật khẩu thành công
                                                Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                                // Chuyển hướng về trang SignInActivity sau khi đổi mật khẩu thành công
                                                Intent intent = new Intent(ChangePassActivity.this, SignInActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // Đổi mật khẩu thất bại
                                                Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // Xác thực thất bại
                            Toast.makeText(ChangePassActivity.this, "Xác thực thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}