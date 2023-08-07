package com.example.aeonmart_demo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aeonmart_demo.Adapter.GioHangAdapter;
import com.example.aeonmart_demo.Model.GioHangModel;
import com.example.aeonmart_demo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private GioHangAdapter adapter;
    private List<GioHangModel> gioHangList;
    private TextView tvTongGia,TextGioHang; // Add this TextView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        db = FirebaseFirestore.getInstance();
        gioHangList = new ArrayList<>();
        recyclerView = findViewById(R.id.GH_rcv);
        tvTongGia = findViewById(R.id.TotalCart); // Initialize the TextView

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GioHangAdapter(this, gioHangList);
        recyclerView.setAdapter(adapter);

        // Load data from Firestore and update the RecyclerView
        loadDataFromFirestore();

        // Update total price when data changes in the adapter
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                double totalPrice = calculateTotalPrice();
                tvTongGia.setText(String.format("%sĐ", totalPrice));
            }
        });


        Button btnThanhToan = findViewById(R.id.GH_btnThanhToan);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GioHangModel> gioHangList = adapter.getGioHangList();
                double totalPrice = calculateTotalPrice();

                Intent intent = new Intent(GioHangActivity.this, XacNhanThanhToanActivity.class);
                intent.putExtra("gioHangList", (Serializable) gioHangList);
                intent.putExtra("totalPrice", totalPrice); // Truyền giá trị TotalCart

                startActivity(intent);
            }
        });
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (GioHangModel gioHangModel : gioHangList) {
            totalPrice += gioHangModel.getProductPrice() * gioHangModel.getProductQuantity();
        }
        return totalPrice;
    }

    private void loadDataFromFirestore() {
        CollectionReference gioHangRef = db.collection("cart");

        gioHangRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    gioHangList.clear(); // Clear the list to avoid duplicates
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert each document to GioHangModel and add it to the list
                        GioHangModel gioHangModel = document.toObject(GioHangModel.class);
                        gioHangList.add(gioHangModel);
                    }
                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle errors
                }
            }
        });
    }
}
