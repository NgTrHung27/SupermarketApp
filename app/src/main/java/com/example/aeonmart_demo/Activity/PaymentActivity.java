package com.example.aeonmart_demo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aeonmart_demo.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

public class PaymentActivity extends AppCompatActivity {
    Button btnXong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnXong = findViewById(R.id.payment_xong);
        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa dữ liệu giỏ hàng từ Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                WriteBatch batch = db.batch();

                db.collection("cart").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            batch.delete(document.getReference());
                        }
                        // Tiến hành xóa dữ liệu từ Firestore
                        batch.commit().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                // Nếu xóa thành công, chuyển về HomeActivity
                                Intent i = new Intent(PaymentActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish(); // Đảm bảo người dùng không thể quay lại PaymentActivity bằng nút back
                            } else {
                                // Xử lý lỗi khi xóa dữ liệu không thành công
                            }
                        });
                    } else {
                        // Xử lý lỗi khi không thể lấy dữ liệu từ Firestore
                    }
                });
            }
        });
    }
}