package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aeonmart_demo.R;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class SignInActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnSignIn, btnSignUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        // Ánh xạ các view từ file XML
        edtEmail = findViewById(R.id.Signin_EDT_Email);
        edtPassword = findViewById(R.id.Signin_EDT_PASS);
        btnSignIn = findViewById(R.id.Signin_BTN_Signin);
        btnSignUp = findViewById(R.id.Signin_BTN_SignUp);
        // Xử lý sự kiện khi người dùng nhấn nút Đăng nhập
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ EditText
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // Kiểm tra xem thông tin đăng nhập có hợp lệ hay không
                if (isValid(email, password)) {
                    // Đăng nhập bằng Firebase
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignInActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Đăng nhập thành công
                                    Intent intent  = new  Intent(getApplicationContext(), Detail_Profile_Activity.class);
                                    startActivity(intent);
                                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    // Ở đây, bạn có thể chuyển đến màn hình chính hoặc thực hiện các hành động khác sau khi đăng nhập thành công
                                } else {
                                    // Đăng nhập thất bại, hiển thị thông báo lỗi tương ứng
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        Toast.makeText(SignInActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        Toast.makeText(SignInActivity.this, "Mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(SignInActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Nếu thông tin đăng nhập không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(SignInActivity.this, "Thông tin đăng nhập không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new  Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Hàm kiểm tra tính hợp lệ của email và password
    private boolean isValid(String email, String password) {
        // Kiểm tra logic đăng nhập hợp lệ của bạn tại đây (ví dụ: không để trống, độ dài tối thiểu, v.v.)
        // Trong ví dụ này, tôi chỉ kiểm tra xem cả hai trường có được điền vào hay không.
        return !email.isEmpty() && !password.isEmpty();
    }
}