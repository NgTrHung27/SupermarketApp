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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Detail_Profile_Activity extends AppCompatActivity {
    private TextView tvName,tvbirth, tvphone, tvcccd, tvaddress, tvemail;
    private Button btnchinhsua;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);

        // Khởi tạo Firebase Auth và Firestore
       btnchinhsua=findViewById(R.id.detailprofile_BTN_Edit);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        //liên kết các textview trong layout
        tvemail=findViewById(R.id.detailprofile_TV_EmailKH);
        tvName=findViewById(R.id.detailprofile_TV_TenKH);

        tvbirth=findViewById(R.id.detailprofile_TV_NSKH);
        tvphone=findViewById(R.id.detailprofile_TV_SDTKH);
        tvcccd=findViewById(R.id.detailprofile_TV_CMNDKH);
        tvaddress=findViewById(R.id.detailprofile_TV_DCKH);
        // Lấy thông tin người dùng từ Firebase Authentication và hiển thị lên giao diện chỉnh sửa
        displayUserInfo();
        btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changchinhsuahoso();
            }
        });
    }
    private void changchinhsuahoso()
    {
        Intent intent = new Intent(Detail_Profile_Activity.this, ChinhSuaHoSoActivity.class);
        startActivityForResult(intent, 1);
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

                            // Set the TextView fields with the user information
                            if (user != null) {
                                tvbirth.setText(user.getBirth());
                                tvphone.setText(user.getPhone());
                                tvcccd.setText(user.getCccd());
                                tvaddress.setText(user.getAddress());
                                // Set TextView tên khách hàng và email
                                tvemail.setText(user.getEmail());
                                tvName.setText(user.getName());
                            }
                        } else {
                            // Document does not exist or is empty
                            Toast.makeText(Detail_Profile_Activity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error occurred while fetching data
                        Toast.makeText(Detail_Profile_Activity.this, "Lỗi: Không thể lấy thông tin người dùng từ Firestore", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Khi nhận được kết quả trả về từ ChinhSuaHoSoActivity
                // Refresh lại activity hiện tại
                finish();
                startActivity(getIntent());
            }
        }
    }
}