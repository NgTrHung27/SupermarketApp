package com.example.aeonmart_demo.Admin;// QuanlynguoidungActivity
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.aeonmart_demo.Adapter.UserAdapter;
import com.example.aeonmart_demo.Model.User;
import com.example.aeonmart_demo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import java.util.ArrayList;
import java.util.List;

public class QuanlynguoidungActivity extends AppCompatActivity {
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

        userAdapter = new UserAdapter(this, userList);
        rv_quanlynguoidung.setAdapter(userAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }

                    userList.clear();

                    for (DocumentChange doc : value.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            User user = doc.getDocument().toObject(User.class);
                            userList.add(user);
                        }
                    }

                    userAdapter.notifyDataSetChanged();
                });

        userAdapter.setOnDeleteButtonClickListener(userEmail -> {
            deleteUserDataInFirestore(userEmail);
        });
    }
    private void deleteUserDataInFirestore(String userEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Delete user data document based on the user's email
        db.collection("users").document(userEmail)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // After deleting user data, delete the corresponding user account in Firebase Authentication
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null && user.getEmail().equals(userEmail)) {
                        user.delete()
                                .addOnSuccessListener(aVoid1 -> {
                                    // Account deleted successfully
                                    Toast.makeText(QuanlynguoidungActivity.this, "Đã xóa tài khoản người dùng", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    // An error occurred while deleting the account
                                    Toast.makeText(QuanlynguoidungActivity.this, "Lỗi: Không thể xóa tài khoản người dùng", Toast.LENGTH_SHORT).show();
                                });
                    }
                    recreate();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(QuanlynguoidungActivity.this, "Lỗi: Không thể xóa dữ liệu người dùng", Toast.LENGTH_SHORT).show();
                });
    }

}
