package com.example.aeonmart_demo.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.aeonmart_demo.Adapter.UserAdapter;
import com.example.aeonmart_demo.Model.User;
import com.example.aeonmart_demo.R;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

public class QuanlynguoidungActivity extends AppCompatActivity {
Button btnxoa;
RecyclerView rv_quanlynguoidung;
List<User> userList;
UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlynguoidung);
        userList = new ArrayList<>();
        rv_quanlynguoidung = findViewById(R.id.rv_quanlynguoidung);
        rv_quanlynguoidung.setHasFixedSize(true);
        rv_quanlynguoidung.setLayoutManager(new LinearLayoutManager(this));
// Gán sự kiện xóa người dùng khi người dùng chọn nút xóa trong RecyclerView

        // Khởi tạo adapter
        userAdapter = new UserAdapter(this, userList);
        rv_quanlynguoidung.setAdapter(userAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .orderBy("name", Query.Direction.ASCENDING) // Sắp xếp theo tên (tùy chọn)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Xử lý lỗi nếu có
                        return;
                    }

                    // Xóa danh sách người dùng hiện tại
                    userList.clear();

                    // Lặp qua các tài liệu trong "users" và thêm vào danh sách người dùng
                    for (DocumentChange doc : value.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String name = doc.getDocument().getString("name");
                            String birth = doc.getDocument().getString("birth");
                            String email = doc.getDocument().getString("email");
                            String phone = doc.getDocument().getString("phone");
                            String cccd = doc.getDocument().getString("cccd");
                            String address = doc.getDocument().getString("address");
                            String password = doc.getDocument().getString("password");
                            String role=doc.getDocument().getString("role");
                            // Tạo đối tượng User và thêm vào danh sách
                            User user = new User(name, birth, email, phone, cccd, address, password,role);
                            userList.add(user);
                        }
                    }

                    // Cập nhật lại RecyclerView
                    userAdapter.notifyDataSetChanged();
                });

        // Đặt sự kiện xóa người dùng khi nhấn nút xóa
        userAdapter.setOnDeleteButtonClickListener(new UserAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClicked(int position) {
                User userToDelete = userList.get(position);
                deleteUserFromFirestore(userToDelete);
            }
        });

    }
    private void deleteUserFromFirestore(User user) {
        // Lấy tham chiếu tới collection "users" trong Firestore
        CollectionReference usersCollectionRef = FirebaseFirestore.getInstance().collection("users");

        // Xác định tài liệu người dùng cần xóa (dựa vào trường ID hoặc một trường duy nhất có giá trị duy nhất để xác định người dùng)
        String userIdToDelete = user.getEmail();
        if (userIdToDelete != null) {
            // Xóa tài liệu người dùng khỏi Firestore
            usersCollectionRef.document(userIdToDelete)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            userAdapter.notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý lỗi nếu xảy ra
                        }
                    });
        }
    }


}