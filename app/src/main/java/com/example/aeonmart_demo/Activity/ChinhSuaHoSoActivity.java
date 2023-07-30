package com.example.aeonmart_demo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aeonmart_demo.Model.User;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChinhSuaHoSoActivity extends AppCompatActivity {

    private EditText edtNameEdit, edtPasswordEdit,edtBirthEdit, edtPhoneEdit, edtCCCDEdit, edtAddressEdit, edtEmailEdit;
    private Button btnSaveChanges;
    TextView tvName,tvEmail;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ho_so);

        // Khởi tạo Firebase Auth và Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        //liên kết các textview trong layout
        tvEmail=findViewById(R.id.CSHS_TV_Email);
        tvName=findViewById(R.id.CSHS_TV_TenKH);
        // Liên kết các view trong layout
        edtNameEdit = findViewById(R.id.CSHS_HOTen);
        edtBirthEdit = findViewById(R.id.CSHS_NGAYSINH);
        edtPhoneEdit = findViewById(R.id.CSHS_SDT);
        edtPasswordEdit = findViewById(R.id.CSHS_MATKHAU);
        edtCCCDEdit = findViewById(R.id.CSHS_CMND);
        edtEmailEdit = findViewById(R.id.CSHS_EMAIL);
        edtAddressEdit = findViewById(R.id.CSHS_DIACHI);
        btnSaveChanges = findViewById(R.id.CSHS_btnLuuThongTin);

        // Lấy thông tin người dùng từ Firebase Authentication và hiển thị lên giao diện chỉnh sửa
        displayUserInfo();

        // Thiết lập sự kiện click cho nút "Lưu thay đổi"
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangesToFirestore();
            }
        });
    }

    private void displayUserInfo() {
        // Lấy thông tin người dùng từ Firebase Authentication
        String email = firebaseAuth.getCurrentUser().getEmail();

        // Hiển thị thông tin người dùng lên giao diện chỉnh sửa
        if (email != null) {

            DocumentReference userRef = firestore.collection("users").document(email);
            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            // Retrieve the User object from Firestore
                            User user = document.toObject(User.class);

                            // Set the EditText fields with the user information
                            if (user != null) {
                                edtNameEdit.setText(user.getName());
                                edtBirthEdit.setText(user.getBirth());
                                edtPhoneEdit.setText(user.getPhone());
                                edtCCCDEdit.setText(user.getCccd());
                                edtAddressEdit.setText(user.getAddress());
                                edtEmailEdit.setText(user.getEmail());
                                edtPasswordEdit.setText(user.getPassword());
                                //set textview tên khách hàng và email
                                tvEmail.setText(user.getEmail());
                                tvName.setText(user.getName());
                            }
                        } else {
                            // Document does not exist or is empty
                            Toast.makeText(ChinhSuaHoSoActivity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error occurred while fetching data
                        Toast.makeText(ChinhSuaHoSoActivity.this, "Lỗi: Không thể lấy thông tin người dùng từ Firestore", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void saveChangesToFirestore() {
        // Lấy thông tin từ các trường EditText sau khi người dùng đã chỉnh sửa
        String newName = edtNameEdit.getText().toString();
        String newPassword = edtPasswordEdit.getText().toString();
        String newBirth = edtBirthEdit.getText().toString();
        String newPhone = edtPhoneEdit.getText().toString();
        String newCCCD = edtCCCDEdit.getText().toString();
        String newAddress = edtAddressEdit.getText().toString();

        // Cập nhật thông tin người dùng trong Firestore
        // Cập nhật thông tin người dùng trong Firestore
        String email = firebaseAuth.getCurrentUser().getEmail();
        if (email != null) {
            DocumentReference userRef = firestore.collection("users").document(email);
            userRef.update(
                    "name", newName,
                    "birth", newBirth,
                    "phone", newPhone,
                    "cccd", newCCCD,
                    "address", newAddress
            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Cập nhật thông tin người dùng thành công trong Firestore

                        // Tiến hành cập nhật mật khẩu mới cho Firebase Authentication
                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        String newPassword = edtPasswordEdit.getText().toString();
                        if (currentUser != null) {
                            currentUser.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Cập nhật mật khẩu thành công trên Firebase Authentication
                                                Toast.makeText(ChinhSuaHoSoActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                                // Kết thúc activity và quay lại màn hình trước đó
                                                Intent intent = new Intent();
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            } else {
                                                // Lỗi khi cập nhật mật khẩu trên Firebase Authentication
                                                Toast.makeText(ChinhSuaHoSoActivity.this, "Lỗi: Không thể cập nhật mật khẩu trên Firebase Authentication", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    } else {
                        // Lỗi khi cập nhật thông tin người dùng trên Firestore
                        Toast.makeText(ChinhSuaHoSoActivity.this, "Lỗi: Không thể cập nhật thông tin người dùng", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
