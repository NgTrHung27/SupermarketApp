package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aeonmart_demo.Detail_HoSo_Activity.ProfileActivity;
import com.example.aeonmart_demo.MainActivity;
import com.example.aeonmart_demo.R;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class SignInActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnSignUp;
    private Button btnsigin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        // Ánh xạ các view từ file XML
        edtEmail = findViewById(R.id.Signin_EDT_Email);
        edtPassword = findViewById(R.id.Signin_EDT_PASS);
        btnSignUp = findViewById(R.id.Signin_BTN_SignUp);
        btnsigin=findViewById(R.id.Signin_BTN_Signin);
        // Xử lý sự kiện khi người dùng nhấn nút Đăng nhập
        clearCartData();

        btnsigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // Kiểm tra xem thông tin đăng nhập có hợp lệ hay không
                if (isValid(email, password)) {
                    // Đăng nhập bằng Firebase
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignInActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Kiểm tra vai trò của người dùng
                                    checkUserRole();
                                    // Không cần khởi chạy ProfileActivity ở đây
                                    // Nó sẽ được xử lý dựa trên vai trò của người dùng trong phương thức checkUserRole()
                                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clearCartData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cartRef = db.collection("cart");

        cartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Xoá từng tài liệu trong collection "cart"
                    document.getReference().delete()
                            .addOnSuccessListener(aVoid -> {
                                // Xử lý khi xoá thành công (tùy chọn)
                            })
                            .addOnFailureListener(e -> {
                                // Xử lý khi xoá thất bại (tùy chọn)
                            });
                }
            } else {
                // Xử lý khi không thể lấy dữ liệu giỏ hàng (tùy chọn)
            }
        });
    }

    // Hàm kiểm tra tính hợp lệ của email và password
    private boolean isValid(String email, String password) {
        // Kiểm tra logic đăng nhập hợp lệ của bạn tại đây (ví dụ: không để trống, độ dài tối thiểu, v.v.)
        // Trong ví dụ này, tôi chỉ kiểm tra xem cả hai trường có được điền vào hay không.
        return !email.isEmpty() && !password.isEmpty();
    }

    private void checkUserRole() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            // Giả sử bạn có một bộ sưu tập có tên là "users" trong Firestore với một trường có tên là "role"
            DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(userEmail);
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String role = documentSnapshot.getString("role");
                    if (role != null) {
                        if (role.equals("admin")) {
                            // Chuyển hướng đến MainActivity cho quản trị viên
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Chuyển hướng đến HomeActivity cho người dùng thông thường
                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        // Kết thúc SignInActivity để ngăn người dùng quay lại nó bằng nút back của thiết bị
                        finish();
                    }
                } else {
                    // Xử lý trường hợp không tìm thấy dữ liệu người dùng
                    Toast.makeText(SignInActivity.this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                // Xử lý bất kỳ lỗi nào xảy ra trong quá trình truy xuất dữ liệu Firestore
                Toast.makeText(SignInActivity.this, "Lỗi khi kiểm tra thông tin người dùng!", Toast.LENGTH_SHORT).show();
            });
        }
    }

}